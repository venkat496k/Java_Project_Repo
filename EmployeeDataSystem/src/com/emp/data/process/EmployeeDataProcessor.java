package com.emp.data.process;

import java.io.Console;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.emp.data.consoleprovider.ConsoleProvider;
import com.emp.data.dto.Employee;
import com.emp.data.filestore.StoreInFile;

public class EmployeeDataProcessor {
	static String numRegex = "^[0-9]+$";
	static List<Employee> recordsInsertList = new ArrayList<Employee>();
	static String fileStorePath = "C:/Test/Employee";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Console consoleReader = ConsoleProvider.getConsole();
		System.out.println("Enter Employee Records data = ");
		while (true) {
			List<String> lines = new ArrayList<String>();
			String input = consoleReader.readLine();
			// System.out.println("Data is = "+input);
			lines.add(input);
			lines.stream().forEach(line -> {
				System.out.println("List of Lines = " + line);
				if (line != null) {
					if (line.contains(",")) {
						if (line.lastIndexOf(",") + 1 == line.length() - 1) {
							System.out.println("Data should not end with comma.");
						} else {
							String data[] = line.split(",");
							Predicate<String> isNumPredicate = str -> str.matches(numRegex);
							List<String> dataEmptyList = Arrays.asList(data).stream().filter(String::isEmpty)
									.collect(Collectors.toList());
							if (dataEmptyList != null && dataEmptyList.size() == 0) {
								boolean isFirNameNum = numberCheck(data[0].trim(), isNumPredicate);
								boolean isLastNameNum = numberCheck(data[1].trim(), isNumPredicate);
								boolean isExpNum = numberCheck(data[2].trim(), isNumPredicate);
								boolean isAgeNum = numberCheck(data[3].trim(), isNumPredicate);
								boolean isOrgNum = numberCheck(data[4].trim(), isNumPredicate);
								if (data[3] != null && isAgeNum && Integer.parseInt(data[3].trim()) <= 0) {
									System.out.println("Invalid Age");
								} else if ((isFirNameNum || isLastNameNum || isOrgNum)
										|| (isFirNameNum && isLastNameNum && isOrgNum) || (!isExpNum || !isAgeNum)
										|| (!isExpNum || !isAgeNum)) {
									System.out.println("Invalid Data Entered.");
								} else {
									Employee e = new Employee();
									e.setEmpFirstName(data[0]);
									e.setEmpLastName(data[1]);
									e.setExperience(Integer.parseInt(data[2].trim()));
									e.setAge(Integer.parseInt(data[3].trim()));
									e.setOrganization(data[4]);
									recordsInsertList.add(e);
								}
							}
						}

					}
					if (line.indexOf(",") < 0 && "exit".equalsIgnoreCase(line)) {
						System.exit(0);
					}
					if (line.indexOf(",") < 0 && "sort".equalsIgnoreCase(line)) {
						System.out.println("SORT Keyword Encountered");
						StoreInFile storeInFileFull = new StoreInFile(fileStorePath + "/" + "EmpFullDetails.txt");
						StoreInFile storeInFileRatio = new StoreInFile(fileStorePath + "/" + "EmpRatioDetails.txt");
						System.out.println("recordsInsertList = " + recordsInsertList);
						Optional.ofNullable(recordsInsertList).orElse(Collections.emptyList()).stream().forEach(emp -> {
							String fullData = (emp.getOrganization() + "," + emp.getExperience() + ","
									+ emp.getEmpFirstName() + "," + emp.getEmpLastName()).trim();
							double ratio = (emp.getExperience() / emp.getAge());
							String ratioData = ("" + ratio + "," + emp.getOrganization()).trim();
							System.out.println("fullData = " + fullData);
							System.out.println("ratioData = " + ratioData);
							storeInFileFull.writeToFile(fullData);
							storeInFileRatio.writeToFile(ratioData);
						});
						System.out.println("Data Storing to Files completed.");
						storeInFileFull.closeFileWriter();
						storeInFileRatio.closeFileWriter();
						if(recordsInsertList != null && recordsInsertList.size() > 0){
							recordsInsertList.clear();
						}
					}
				}
			});
		}

	}

	private static boolean numberCheck(String input, Predicate<String> numPred) {
		if (input == null)
			return false;
		return numPred.test(input);
	}

}
