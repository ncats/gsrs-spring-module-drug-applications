package gov.nih.ncats.application.application.exporters;

import gov.nih.ncats.application.SubstanceModuleService;
import gov.nih.ncats.application.application.controllers.ApplicationController;
import gsrs.springUtils.AutowireHelper;
import ix.ginas.exporters.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

;

public class ApplicationExporterFactory implements ExporterFactory {

	@Autowired
	public ApplicationController applicationController;

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
	public ApplicationExporter createNewExporter(OutputStream out, Parameters params) throws IOException {

		if (applicationController == null) {
			AutowireHelper.getInstance().autowire(this);
		}

		SpreadsheetFormat format = SpreadsheetFormat.XLSX;
		Spreadsheet spreadsheet = format.createSpeadsheet(out);

		ApplicationExporter.Builder builder = new ApplicationExporter.Builder(spreadsheet);
		configure(builder, params);
		
		return builder.build(applicationController);
	}

	protected void configure(ApplicationExporter.Builder builder, Parameters params) {
		builder.includePublicDataOnly(params.publicOnly());
	}

}
