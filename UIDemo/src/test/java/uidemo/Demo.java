package uidemo;

import static uidemo.ObjectRepository.*;

public class Demo {

	public static void main(String[] args) {
		LoggerUtil.initialize();
		try {

			LoggerUtil.startTestCase("ADD_SONY_VAIO", "Adding sony vaio in cart");
			SelCommonUtil.initialize();
			SelCommonUtil.launchClient("https://www.demoblaze.com");
			LoggerUtil.logInfo("Application launched: https://www.demoblaze.com");
			addIntoCart(SONY_VAIO_I5_LINK);
			LoggerUtil.endTestCase();

			LoggerUtil.startTestCase("ADD_DELL", "ADDING dell in cart");
			SelCommonUtil.click(HOME_LINK);
			LoggerUtil.logInfo("Home button clicked");
			addIntoCart(DEL_I7_8GB_LINK);
			LoggerUtil.endTestCase();

			LoggerUtil.startTestCase("Delete_Dell", "Deleting Dell from cart");
			SelCommonUtil.click(CART_BTN);
			LoggerUtil.logInfo("CART button clicked");
			deleteFromCART("Dell i7 8gb");
			LoggerUtil.endTestCase();

			LoggerUtil.startTestCase("Place_Order", "Placing the Order");
			clickPlaceOrder();
			fillPurchaseForm("demo", "India", "Delhi", "123456789", "Jan", "2021");
			SelCommonUtil.scrollIntoView(PURCHASE_BUTTON);
			SelCommonUtil.click(PURCHASE_BUTTON);
			LoggerUtil.logInfo("Purchase button clicked");
					
			verifyPurchaseSummary();
			SelCommonUtil.click(OK_BTN);
			LoggerUtil.logInfo("OK button clicked");
			
			SelCommonUtil.quit();
			LoggerUtil.endTestCase();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			LoggerUtil.close();
		}

	}

	private static void verifyPurchaseSummary() {
		String[] PURCHASE_Summary = { "XPATH", ".//h2[text()='Thank you for your purchase!']//following-sibling::p" };

		String purchase_Summary = SelCommonUtil.getText(PURCHASE_Summary);
		String[] allPurchaceInfo = purchase_Summary.split("\n");
		String id="", amount = "";
		for (String purchaseInfo : allPurchaceInfo) {
			String[] eachItem = purchaseInfo.split(":");
			if (eachItem[0].equals("Id")) {
				id = eachItem[1].trim();
			}
			if (eachItem[0].equals("Amount")) {
				amount = eachItem[1].trim();
				break;
			}
		}
		LoggerUtil.logInfo("Purchase made with id: "+id);
		if (amount.equals("790 USD")) {
			LoggerUtil.logPass("AMOUNT is as expected " + amount);
		} else {
			LoggerUtil.logPass("AMOUNT is not as expected, expected: 790 USD, actual :" + amount);

		}
	}

	public static void clickPlaceOrder() {
		try {
			SelCommonUtil.click(PLACE_ORDER_BTN);
			LoggerUtil.logInfo("Place Order button clicked");
			
			if (!SelCommonUtil.isDisplayed(NAME_TEXTBOX)) {
				SelCommonUtil.click(PLACE_ORDER_BTN);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addIntoCart(String[] specificObjToBeAdded) {
		try {
			SelCommonUtil.scrollIntoView(LAPTOP_LINK);
			SelCommonUtil.click(LAPTOP_LINK);
			LoggerUtil.logInfo("LAPTOP link clicked");
			SelCommonUtil.click(specificObjToBeAdded);
			LoggerUtil.logInfo(specificObjToBeAdded[1] + " clicked");

			SelCommonUtil.waitTillExists(ADD_TO_CART_LINK);
			SelCommonUtil.click(ADD_TO_CART_LINK);
			LoggerUtil.logInfo("Add to cart clicked");

			SelCommonUtil.waitForAlert();
			SelCommonUtil.acceptAlert();
			LoggerUtil.logInfo("Accepted the alert dialog box");

		} catch (Exception e) {
			LoggerUtil.logFail("EXCEPTION OCUURED while adding " + specificObjToBeAdded[1] + " into cart");
		}
	}

	public static void deleteFromCART(String itemToBeDeleted) {
		try {
			String[] deleteButton = { "XPATH",
					".//td[text()='" + itemToBeDeleted + "']//following-sibling::td//descendant::a" };
			SelCommonUtil.waitTillExists(PLACE_ORDER_BTN);
			SelCommonUtil.click(deleteButton);
			LoggerUtil.logInfo("Delete button clicked for "+itemToBeDeleted);
			SelCommonUtil.isClickable(PLACE_ORDER_BTN);
			SelCommonUtil.wait(3);

		} catch (Exception e) {
			LoggerUtil.logFail("EXCEPTION OCUURED while deleting " + itemToBeDeleted + " from cart");
		}
	}

	public static void fillPurchaseForm(String name, String country, String city, String creditCard, String month,
			String year) {

		try {

			SelCommonUtil.waitTillExists(NAME_TEXTBOX);
			
			SelCommonUtil.setText(NAME_TEXTBOX, name);
			LoggerUtil.logInfo("Name set as "+name);
			SelCommonUtil.setText(COUNTRY_TEXTBOX, country);
			LoggerUtil.logInfo("Country set as "+country);
			SelCommonUtil.setText(CITY_TEXTBOX, city);
			LoggerUtil.logInfo("City set as "+city);
			SelCommonUtil.setText(CARD_TEXTBOX, creditCard);
			LoggerUtil.logInfo("Card set as "+creditCard);
			SelCommonUtil.setText(MONTH_TEXTBOX, month);
			LoggerUtil.logInfo("Month set as "+month);
			SelCommonUtil.setText(YEAR_TEXTBOX, year);
			LoggerUtil.logInfo("Year set as "+year);
			
		} catch (Exception e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}
}
