package aquamen.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Professional spreadsheet exporter for financial records.
 * Creates / appends to a CSV file that opens cleanly in Microsoft Excel,
 * Google Sheets, LibreOffice Calc, etc. with proper columns.
 */
public class ExcelExporter {

    // File will be created next to where the application is run
    private static final String FILE_NAME = "Aquamen_Financial_Records.csv";
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Appends one financial record to the spreadsheet.
     * Creates the file with headers if it does not exist yet.
     *
     * @return the absolute path of the spreadsheet file
     */
    public static String exportFinancialRecord(int sales, int gallons, int expenses) throws IOException {
        File file = new File(FILE_NAME);
        boolean isNewFile = !file.exists() || file.length() == 0;

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true))) {
            // Write professional header only once
            if (isNewFile) {
                writer.println("Date,Time,Sales (PHP),Gallons Refilled,Expenses (PHP),Net Profit (PHP),Recorded By");
            }

            LocalDateTime now = LocalDateTime.now();
            int netProfit = sales - expenses;

            // Clean CSV row (no commas inside values that need escaping for these simple numbers)
            writer.printf("%s,%s,%d,%d,%d,%d,%s%n",
                    now.format(DATE_FMT),
                    now.format(TIME_FMT),
                    sales,
                    gallons,
                    expenses,
                    netProfit,
                    "Admin"
            );
        }

        return file.getAbsolutePath();
    }

    /**
     * Returns the current spreadsheet file path (even if it does not exist yet).
     */
    public static String getSpreadsheetPath() {
        return new File(FILE_NAME).getAbsolutePath();
    }

    /**
     * Opens the spreadsheet with the default system application (Excel, Sheets, etc.)
     * if the OS supports it.
     */
    public static void openSpreadsheet() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            throw new IOException("Spreadsheet file not found. Confirm at least one record first.");
        }
        if (java.awt.Desktop.isDesktopSupported()) {
            java.awt.Desktop.getDesktop().open(file);
        } else {
            throw new IOException("Cannot open file automatically on this system. Please open manually:\n" + file.getAbsolutePath());
        }
    }
}
