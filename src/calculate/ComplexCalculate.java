package calculate;

public  class ComplexCalculate {
	
	
	public static String calculateString(String[] tokenArray) {
		if (tokenArray.length > 0) {
			return calculateString(tokenArray, 0, "");
		}
		return "";
		
	}
	
	private static String isFormated(String equation) {
		
		
		if (equation.contains("+")) {
			return "+";
		} else if (equation.contains("-")) {
			return "-";
		} else if (equation.contains("x")) {
			return "x";
		} else if(equation.contains("/")) {
			return "/";
		}
		return "";
	}
	
	
	private static String equationChunk(String[] eSplit, String operator) {
		
		if (eSplit.length != 2) {
			return "Bad input";
		}
		
		Double numVal;
		String result = "";
		switch (operator) {
		case "+":
			numVal = Double.parseDouble(eSplit[0]) + Double.parseDouble(eSplit[1]);
			result = Double.toString(numVal);
			break;
		case "-":
			numVal = Double.parseDouble(eSplit[0]) - Double.parseDouble(eSplit[1]);
			result = Double.toString(numVal);
			break;
		case "x":
			numVal = Double.parseDouble(eSplit[0]) * Double.parseDouble(eSplit[1]);
			result = Double.toString(numVal);
			break;
		case "/":
			if (Integer.parseInt(eSplit[2]) == 0) {
				throw new IllegalArgumentException("must be a non 0 number");
			}
			numVal = Double.parseDouble(eSplit[0]) / Double.parseDouble(eSplit[1]);
			result = Double.toString(numVal);
			break;
		}
		return result;
	}
	
	private static String calculateString(String[] tokenArray, int index, 
			String equation) {
		if (index >= tokenArray.length) {
			return equation;
		}
		
		equation += tokenArray[index];
		
		if (equation.length() >= 3) {
			
			String operator = isFormated(equation);
			String[] chunkedEq = equation.split("");
			if (operator != "" && isFormated(chunkedEq[chunkedEq.length - 1]) == "") {
				if(operator == "+") {
					operator = "\\+";
				}
				chunkedEq = equation.split(operator);
				if (operator == "\\+") {
					operator = "+";
				}
				equation = equationChunk(chunkedEq, operator);	
				return calculateString(tokenArray, index + 1, equation);
			}
		}
		
		
		equation = calculateString(tokenArray, index + 1, equation);
		
		return equation;
		
	}
}
