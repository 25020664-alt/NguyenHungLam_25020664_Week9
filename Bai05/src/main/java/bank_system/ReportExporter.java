package bank_system;

import java.io.File;

/**
 * Utility class for building export file paths.
 */
public class ReportExporter {

  /**
   * Builds a report path using a hardcoded Windows separator (BUGGY on Linux/macOS).
   *
   * @param baseDir base directory
   * @param fileName report file name
   * @return constructed file path
   */
  public String buildReportPath(String baseDir, String fileName) {
    return baseDir + "\\" + fileName;
  }
}
