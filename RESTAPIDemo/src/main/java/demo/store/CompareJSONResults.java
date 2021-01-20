package demo.store;

import java.util.Map.Entry;
import java.util.Properties;

public class CompareJSONResults {
	private boolean verificationSuccessfull = false;
	private int actKeyCount = 0;
	private int benchKeyCount = 0;
	private String extraKeyDetails = null;
	private Properties extraBenchValue = new Properties();
	private Properties extraActValue = new Properties();
	private Properties diffBenchValue = new Properties();
	private Properties diffActValue = new Properties();
	private String message = "";

	public String toString() {
		if (verificationSuccessfull == true) {
			return "Comparision Successful";
		} else {
			String str = "Comparision for RESPONSE BODY failed \n";
			if (actKeyCount != benchKeyCount) {
				str = str + " Keys are different between runtime and benchmark \n" + "Runtime json count: "
						+ actKeyCount + "\n " + "Bench json count " + benchKeyCount + "\n" + "Extra Key(s) "
						+ getExtraKeyDetails();
			}
			if(!printdiffs().equals("")) {
				str = str +"Values are different between runtime and benchmark \n"
						+printdiffs();
				str = str+" "+ message;
			}
			return str;
		}
	}

	public String printdiffs() {
		String diffs = "";
		for (Entry<Object, Object> entry : diffBenchValue.entrySet()) {
			String key = (String) entry.getKey();
			Object benchVal = diffBenchValue.get(entry.getKey());
			Object actVal = diffActValue.get(entry.getKey());
			diffs = diffs + "key: >>>" + key + " bench value: '" + benchVal + "' actual value: '" + actVal + "'\n";
		}
		return diffs;
	}

	public boolean isVerificationSuccessfull() {
		return verificationSuccessfull;
	}

	public void setVerificationSuccessfull(boolean verificationSuccessfull) {
		this.verificationSuccessfull = verificationSuccessfull;
	}

	public int getActKeyCount() {
		return actKeyCount;
	}

	public void setActKeyCount(int actKeyCount) {
		this.actKeyCount = actKeyCount;
	}

	public int getBenchKeyCount() {
		return benchKeyCount;
	}

	public void setBenchKeyCount(int benchKeyCount) {
		this.benchKeyCount = benchKeyCount;
	}

	public String getExtraKeyDetails() {
		return extraKeyDetails;
	}

	public void setExtraKeyDetails(String extraKeyDetails) {
		this.extraKeyDetails = extraKeyDetails;
	}

	public Properties getExtraBenchValue() {
		return extraBenchValue;
	}

	public void setExtraBenchValue(Properties extraBenchValue) {
		this.extraBenchValue = extraBenchValue;
	}

	public Properties getExtraActValue() {
		return extraActValue;
	}

	public void setExtraActValue(Properties extraActValue) {
		this.extraActValue = extraActValue;
	}

	public Properties getDiffBenchValue() {
		return diffBenchValue;
	}

	public void setDiffBenchValue(Properties diffBenchValue) {
		this.diffBenchValue = diffBenchValue;
	}

	public Properties getDiffActValue() {
		return diffActValue;
	}

	public void setDiffActValue(Properties diffActValue) {
		this.diffActValue = diffActValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
