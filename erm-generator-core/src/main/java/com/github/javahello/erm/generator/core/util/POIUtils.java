package com.github.javahello.erm.generator.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;

public class POIUtils {

	public static class CellLocation {
		public int r;

		public int c;

		private CellLocation(int r, short c) {
			this.r = r;
			this.c = c;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			String str = "(" + this.r + ", " + this.c + ")";

			return str;
		}
	}

	public static CellLocation findCell(HSSFSheet sheet, String str) {
		return findCell(sheet, new String[] { str });
	}

	public static CellLocation findCell(HSSFSheet sheet, String[] strs) {
		for (int rowNum = sheet.getFirstRowNum(); rowNum < sheet
				.getLastRowNum() + 1; rowNum++) {
			HSSFRow row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			for (int i = 0; i < strs.length; i++) {
				Integer colNum = findColumn(row, strs[i]);

				if (colNum != null) {
					return new CellLocation(rowNum, colNum.shortValue());
				}
			}
		}

		return null;
	}

	public static Integer findColumn(HSSFRow row, String str) {
		for (int colNum = row.getFirstCellNum(); colNum <= row.getLastCellNum(); colNum++) {
			HSSFCell cell = row.getCell(colNum);

			if (cell == null) {
				continue;
			}

			if (cell.getCellType() == CellType.STRING) {
				HSSFRichTextString cellValue = cell.getRichStringCellValue();

				if (str.equals(cellValue.getString())) {
					return Integer.valueOf(colNum);
				}
			}
		}

		return null;
	}

	public static CellLocation findMatchCell(HSSFSheet sheet, String regexp) {
		for (int rowNum = sheet.getFirstRowNum(); rowNum < sheet
				.getLastRowNum() + 1; rowNum++) {
			HSSFRow row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			Integer colNum = findMatchColumn(row, regexp);

			if (colNum != null) {
				return new CellLocation(rowNum, colNum.shortValue());
			}
		}

		return null;
	}

	public static Integer findMatchColumn(HSSFRow row, String str) {
		for (int colNum = row.getFirstCellNum(); colNum <= row.getLastCellNum(); colNum++) {
			HSSFCell cell = row.getCell(colNum);

			if (cell == null) {
				continue;
			}

			if (cell.getCellType() != CellType.STRING) {
				continue;
			}

			HSSFRichTextString cellValue = cell.getRichStringCellValue();

			if (cellValue.getString().matches(str)) {
				return Integer.valueOf(colNum);
			}
		}

		return null;
	}

	public static CellLocation findCell(HSSFSheet sheet, String str, int colNum) {
		for (int rowNum = sheet.getFirstRowNum(); rowNum < sheet
				.getLastRowNum() + 1; rowNum++) {
			HSSFRow row = sheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			HSSFCell cell = row.getCell(colNum);

			if (cell == null) {
				continue;
			}
			HSSFRichTextString cellValue = cell.getRichStringCellValue();

			if (!DiffHelper.isEmpty(cellValue.getString())) {
				if (cellValue.getString().equals(str)) {
					return new CellLocation(rowNum, (short) colNum);
				}
			}
		}

		return null;
	}

	public static void replace(HSSFSheet sheet, String keyword, String str) {
		CellLocation location = findCell(sheet, keyword);

		if (location == null) {
			return;
		}

		setCellValue(sheet, location, str);
	}

	public static String getCellValue(HSSFSheet sheet, CellLocation location) {
		HSSFRow row = sheet.getRow(location.r);
		HSSFCell cell = row.getCell(location.c);

		HSSFRichTextString cellValue = cell.getRichStringCellValue();

		return cellValue.toString();
	}

	public static String getCellValue(HSSFSheet sheet, int r, int c) {
		HSSFRow row = sheet.getRow(r);

		if (row == null) {
			return null;
		}

		HSSFCell cell = row.getCell(c);

		if (cell == null) {
			return null;
		}

		HSSFRichTextString cellValue = cell.getRichStringCellValue();

		return cellValue.toString();
	}

	public static int getIntCellValue(HSSFSheet sheet, int r, int c) {
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			return 0;
		}
		HSSFCell cell = row.getCell(c);

		try {
			if (cell.getCellType() != CellType.NUMERIC) {
				return 0;
			}
		} catch (RuntimeException e) {
			System.err.println("Exception at sheet name:"
					+ sheet.getSheetName() + ", row:" + (r + 1) + ", col:"
					+ (c + 1));
			throw e;
		}

		return (int) cell.getNumericCellValue();
	}

	public static boolean getBooleanCellValue(HSSFSheet sheet, int r, int c) {
		HSSFRow row = sheet.getRow(r);

		if (row == null) {
			return false;
		}

		HSSFCell cell = row.getCell(c);

		if (cell == null) {
			return false;
		}

		try {
			return cell.getBooleanCellValue();
		} catch (RuntimeException e) {
			System.err.println("Exception at sheet name:"
					+ sheet.getSheetName() + ", row:" + (r + 1) + ", col:"
					+ (c + 1));
			throw e;
		}
	}

	public static short getCellColor(HSSFSheet sheet, int r, int c) {
		HSSFRow row = sheet.getRow(r);
		if (row == null) {
			return -1;
		}
		HSSFCell cell = row.getCell(c);

		return cell.getCellStyle().getFillForegroundColor();
	}

	public static void setCellValue(HSSFSheet sheet, CellLocation location,
			String value) {
		HSSFRow row = sheet.getRow(location.r);
		HSSFCell cell = row.getCell(location.c);

		HSSFRichTextString text = new HSSFRichTextString(value);
		cell.setCellValue(text);
	}

	public static HSSFWorkbook readExcelBook(File excelFile) throws IOException {
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(excelFile);

			return readExcelBook(fis);

		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	public static HSSFWorkbook readExcelBook(InputStream stream)
			throws IOException {
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(stream);
			return new HSSFWorkbook(bis);

		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}

	public static void writeExcelFile(File excelFile, HSSFWorkbook workbook)
			throws Exception {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			fos = new FileOutputStream(excelFile);
			bos = new BufferedOutputStream(fos);
			workbook.write(bos);

		}finally {
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	public static CellRangeAddress getMergedRegion(HSSFSheet sheet,
			CellLocation location) {
		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress region = sheet.getMergedRegion(i);

			int rowFrom = region.getFirstRow();
			int rowTo = region.getLastRow();

			if (rowFrom == location.r && rowTo == location.r) {
				int colFrom = region.getFirstColumn();

				if (colFrom == location.c) {
					return region;
				}
			}
		}

		return null;
	}

	public static List<CellRangeAddress> getMergedRegionList(HSSFSheet sheet,
			int rowNum) {
		List<CellRangeAddress> regionList = new ArrayList<CellRangeAddress>();

		for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
			CellRangeAddress region = sheet.getMergedRegion(i);

			int rowFrom = region.getFirstRow();
			int rowTo = region.getLastRow();

			if (rowFrom == rowNum && rowTo == rowNum) {
				regionList.add(region);
			}
		}

		return regionList;
	}

	public static void copyRow(HSSFSheet oldSheet, HSSFSheet newSheet,
			int oldRowNum, int newRowNum) {
		HSSFRow oldRow = oldSheet.getRow(oldRowNum);

		HSSFRow newRow = newSheet.createRow(newRowNum);

		if (oldRow == null) {
			return;
		}

		newRow.setHeight(oldRow.getHeight());

		if (oldRow.getFirstCellNum() == -1) {
			return;
		}

		for (int colNum = oldRow.getFirstCellNum(); colNum <= oldRow
				.getLastCellNum(); colNum++) {
			HSSFCell oldCell = oldRow.getCell(colNum);
			HSSFCell newCell = newRow.createCell(colNum);

			if (oldCell != null) {
				HSSFCellStyle style = oldCell.getCellStyle();
				newCell.setCellStyle(style);

				CellType cellType = oldCell.getCellType();
				newCell.setCellType(cellType);

				if (cellType == CellType.BOOLEAN) {
					newCell.setCellValue(oldCell.getBooleanCellValue());

				} else if (cellType == CellType.FORMULA) {
					newCell.setCellFormula(oldCell.getCellFormula());

				} else if (cellType == CellType.NUMERIC) {
					newCell.setCellValue(oldCell.getNumericCellValue());

				} else if (cellType == CellType.STRING) {
					newCell.setCellValue(oldCell.getRichStringCellValue());
				}
			}
		}

		POIUtils.copyMergedRegion(newSheet,
				getMergedRegionList(oldSheet, oldRowNum), newRowNum);
	}

	public static void copyMergedRegion(HSSFSheet sheet,
			List<CellRangeAddress> regionList, int rowNum) {
		for (CellRangeAddress region : regionList) {
			CellRangeAddress address = new CellRangeAddress(rowNum, rowNum,
					region.getFirstColumn(), region.getLastColumn());
			sheet.addMergedRegion(address);
		}
	}




	public static HSSFFont copyFont(HSSFWorkbook workbook, HSSFFont font) {

		HSSFFont newFont = workbook.createFont();

		// newFont.setBoldweight(font.getBoldweight());
		// newFont.setCharSet(font.getCharSet());
		// newFont.setColor(font.getColor());
		// newFont.setFontHeight(font.getFontHeight());
		// newFont.setFontHeightInPoints(font.getFontHeightInPoints());
		// newFont.setFontName(font.getFontName());
		// newFont.setItalic(font.getItalic());
		// newFont.setStrikeout(font.getStrikeout());
		// newFont.setTypeOffset(font.getTypeOffset());
		// newFont.setUnderline(font.getUnderline());

		return newFont;
	}

	public static HSSFRow insertRow(HSSFSheet sheet, int rowNum) {
		sheet.shiftRows(rowNum + 1, sheet.getLastRowNum(), 1);

		return sheet.getRow(rowNum);
	}
}
