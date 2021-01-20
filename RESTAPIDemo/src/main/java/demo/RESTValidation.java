package demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;

import demo.store.CompareJSONResults;
import demo.store.RESTTestDefinition;
import demo.store.ResponseBean;

public class RESTValidation {

	private RESTTestDefinition testinfoObj = null;
	private ResponseBean actualResponseObj;

	public RESTValidation(RESTTestDefinition rtDef) {
		this.testinfoObj = rtDef;
		this.actualResponseObj = testinfoObj.getActualResponse();
	}

	public void validateResponseCode() throws Exception {
		int expResponseCode = testinfoObj.getExpectedResponse().getStatusCode();
		int actualResponseCode = actualResponseObj.getStatusCode();

		if (expResponseCode == actualResponseCode) {
			//System.out.println("Actual and Expected status matched");
			LoggerUtil.logPass("Actual and Expected status matched");
		} else {
			LoggerUtil.logFail("Actual Status : " + actualResponseCode + " Exp Status " + expResponseCode);
			;
		}
	}

	public void validateResponseBodyByIgnoringField(String fieldToIgnore) {
		String expResponseBody = testinfoObj.getExpectedResponse().getResponseBodyJson();
		String actualResponse = actualResponseObj.getResponseBodyJson();
		createOutputFile();
		CompareJSONResults compare = compareJSON(expResponseBody, actualResponse, fieldToIgnore);
		if (compare.isVerificationSuccessfull()) {
			LoggerUtil.logPass("Actual and expected response MATCHED");

		} else {
			LoggerUtil.logFail(compare.toString());
		}
	}

	private CompareJSONResults compareJSON(String expResponseBody, String actualResponse, String fieldToIgnore) {
		CompareJSONResults compareResults = new CompareJSONResults();
		compareResults.setVerificationSuccessfull(true);
		JSONCompareMode mode = JSONCompareMode.LENIENT;
		JSONComparator comparator = new CustomComparator(mode, new Customization(fieldToIgnore, (o1, o2) -> true));
		try {
			JSONCompareResult result = JSONCompare.compareJSON(expResponseBody, actualResponse, comparator);
			List<FieldComparisonFailure> fieldFailures = result.getFieldFailures();
			List<FieldComparisonFailure> fieldMissing = result.getFieldMissing();
			List<FieldComparisonFailure> fieldUnxpected = result.getFieldUnexpected();
			if (!result.passed()) {
				String errorMessage = result.getMessage();
				compareResults.setVerificationSuccessfull(false);
				if (!fieldMissing.isEmpty() || !fieldUnxpected.isEmpty()) {
					List<String> missingFieldsString = new ArrayList<>();
					for (FieldComparisonFailure fMissing : fieldMissing) {
						missingFieldsString.add(fMissing.getExpected().toString());
					}

					List<String> unexpectedFieldsString = new ArrayList<>();
					for (FieldComparisonFailure fUnexpected : fieldUnxpected) {
						unexpectedFieldsString.add(fUnexpected.getActual().toString());
					}

					Collections.sort(missingFieldsString);
					Collections.sort(unexpectedFieldsString);

					int mismatchFiledCount = missingFieldsString.size() > unexpectedFieldsString.size()
							? unexpectedFieldsString.size()
							: missingFieldsString.size();
					String keyName, benchValue, actualValue;

					for (int i = 0; i < mismatchFiledCount; i++) {
						keyName = fieldMissing.get(i).getField().replace("[]", "[" + i + "]");
						benchValue = missingFieldsString.get(i).toString();
						actualValue = unexpectedFieldsString.get(i).toString();
						Properties diffBenchValues = compareResults.getDiffBenchValue();
						diffBenchValues.put(keyName, benchValue);
						compareResults.setDiffBenchValue(diffBenchValues);
						Properties diffActValues = compareResults.getDiffActValue();
						diffActValues.put(keyName, actualValue);
						compareResults.setDiffActValue(diffActValues);
					}
					if (unexpectedFieldsString.size() > mismatchFiledCount) {
						errorMessage = errorMessage + " There are still ("
								+ (unexpectedFieldsString.size() - mismatchFiledCount) + ") extra keys in Actual JSON";
					} else if (missingFieldsString.size() > mismatchFiledCount) {
						errorMessage = errorMessage + " There are still ("
							+ (missingFieldsString.size() - mismatchFiledCount) + ") extra keys in bench JSON";

					}

				}

				if (fieldFailures != null && fieldFailures.size() > 0) {
					for (FieldComparisonFailure fieldComparisonFailure : fieldFailures) {
						String fieldName = fieldComparisonFailure.getField();
						Object benchValue = fieldComparisonFailure.getExpected();
						Object actualValue = fieldComparisonFailure.getActual();
					if (!benchValue.getClass().equals(actualValue.getClass())) {
							String benchClass = benchValue.getClass().toString();
							String actualClass = actualValue.getClass().toString();
							String[] benchClassName = benchClass.split("\\.");
							String[] actualClassName = actualClass.split("\\.");
							errorMessage = errorMessage + " key: >>> '" + fieldName + "<br>' Comparing "
								+ benchClassName[benchClassName.length - 1] + " with "
									+ actualClassName[actualClassName.length - 1]
									+ "..Comaprison Failed due to type mismatch..";
							continue;

						} else if (benchValue.toString().trim().equals(actualValue.toString().trim())) {
							continue;
						}
						
						Properties diffBenchValues = compareResults.getDiffBenchValue();
						diffBenchValues.put(fieldName, benchValue);
						compareResults.setDiffBenchValue(diffBenchValues);
						Properties diffActValues = compareResults.getDiffActValue();
						diffActValues.put(fieldName, actualValue);
						compareResults.setDiffActValue(diffActValues);
					}
					compareResults.setVerificationSuccessfull(false);
				}
				compareResults.setMessage(errorMessage);
			}
		} catch (

		JSONException e) {
			compareResults.setVerificationSuccessfull(false);
			String message = "JSON Comparison failed: " + e.getMessage();
			compareResults.setMessage(message);
			e.printStackTrace();
		} catch (Exception e) {
			compareResults.setVerificationSuccessfull(false);
			String message = "JSON Comparison failed: " + e.getMessage();
			compareResults.setMessage(message);
			e.printStackTrace();
		}

		return compareResults;

	}


	private void createOutputFile() {
		String expResponseBodyFile = testinfoObj.getExpectedResponse().getResponseBodyFile();
		String actualResponseBodyFile = expResponseBodyFile.replace("BenchmarkFolder", "OutputFolder");
		try {
			FileUtil.deleteFile(actualResponseBodyFile);
			FileUtil.createTextFile(actualResponseBodyFile,
					GSONUtil.prettyPrintJson(actualResponseObj.getResponseBodyJson()));
		} catch (IOException e) {
			// e.printStackTrace();
		}

	}
}
