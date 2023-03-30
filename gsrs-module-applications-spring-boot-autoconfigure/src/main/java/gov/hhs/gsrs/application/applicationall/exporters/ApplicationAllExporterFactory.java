package gov.hhs.gsrs.application.applicationall.exporters;

import gov.hhs.gsrs.application.SubstanceModuleService;
import gov.hhs.gsrs.application.applicationall.controllers.ApplicationAllController;

import gsrs.DefaultDataSourceConfig;
import gsrs.springUtils.AutowireHelper;
import ix.ginas.exporters.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ApplicationAllExporterFactory implements ExporterFactory {

	@PersistenceContext(unitName =  DefaultDataSourceConfig.NAME_ENTITY_MANAGER)
	public EntityManager entityManager;

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
		Spreadsheet spreadsheet = format.createSpreadsheet(out);

		ApplicationAllExporter.Builder builder = new ApplicationAllExporter.Builder(spreadsheet);
		configure(builder, params);
		
		return builder.build(applicationController, entityManager);
	}

	protected void configure(ApplicationAllExporter.Builder builder, Parameters params) {
		builder.includePublicDataOnly(params.publicOnly());
	}

}
