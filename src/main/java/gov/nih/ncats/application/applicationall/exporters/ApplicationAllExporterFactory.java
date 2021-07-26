package gov.nih.ncats.application.applicationall.exporters;

import gov.nih.ncats.application.applicationall.controllers.ApplicationAllController;
import gov.nih.ncats.application.SubstanceModuleService;
import ix.ginas.exporters.*;
import gsrs.springUtils.AutowireHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;;
import java.util.*;

public class ApplicationAllExporterFactory implements ExporterFactory {
	@Autowired
	public ApplicationAllController applicationController;

	@Autowired
	public SubstanceModuleService substanceModuleService;

	private static final Set<OutputFormat> FORMATS;

	static {
		Set<OutputFormat> set = new LinkedHashSet<>();
		set.add(SpreadsheetFormat.TSV);
		set.add(SpreadsheetFormat.CSV);
		set.add(SpreadsheetFormat.XLSX);

		FORMATS = Collections.unmodifiableSet(set);
	}

	@Override
	public Set<OutputFormat> getSupportedFormats() {
		return FORMATS;
	}

	@Override
	public boolean supports(Parameters params) {
		return params.getFormat() instanceof SpreadsheetFormat;
	}

	@Override
	public ApplicationAllExporter createNewExporter(OutputStream out, Parameters params) throws IOException {

		if (applicationController == null) {
			AutowireHelper.getInstance().autowire(this);
		}

		SpreadsheetFormat format = SpreadsheetFormat.XLSX;
		Spreadsheet spreadsheet = format.createSpeadsheet(out);

		ApplicationAllExporter.Builder builder = new ApplicationAllExporter.Builder(spreadsheet);
		configure(builder, params);
		
		return builder.build(applicationController);
	}

	protected void configure(ApplicationAllExporter.Builder builder, Parameters params) {
		builder.includePublicDataOnly(params.publicOnly());
	}

}