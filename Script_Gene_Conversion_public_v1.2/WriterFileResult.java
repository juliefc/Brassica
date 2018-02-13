import java.util.Iterator;
import java.util.Vector;

public class WriterFileResult {
	private static final String SEPARATEUR	=	"	";

	private String lineSeparator;
	private StringBuffer stringBuffer;
	
	public WriterFileResult() {
		//if (SystemUtils.IS_OS_WINDOWS) {
			lineSeparator = System.getProperty("line.separator");
		//}
		//else if (SystemUtils.IS_OS_LINUX) {
		//	lineSeparator = "\n";
		//}
		
		stringBuffer = new StringBuffer();
		stringBuffer.append("Gene" + SEPARATEUR + "Variety" + SEPARATEUR + "All HomeoSNPs" + SEPARATEUR + "HomeoSNPs retained in B. napus" + SEPARATEUR + "Putative Autapomorphy in B. napus A" + SEPARATEUR + "Putative Autapomorphy in B. napus C" + SEPARATEUR + "Putative SNP conversion in B. napus C-->A" + SEPARATEUR + "Putative SNP conversion in B. napus A-->C" + SEPARATEUR + "Putative Gene conversion event C-->A" + SEPARATEUR + "Borders C-->A" + SEPARATEUR + "Putative Gene conversion event A-->C" + SEPARATEUR + "Borders A-->C" + SEPARATEUR + "Alignment size (bp)");
	}
	
	private int getNbConversion(Vector<Conversion> conversions, String type) {
		int nbConversionOfOneType = 0;
		Iterator<Conversion> itConversion = conversions.iterator();
		while (itConversion.hasNext()) {
			Conversion conversion = itConversion.next();
			if (conversion.getType().equals(type)) {
				++nbConversionOfOneType;
			}
		}
		return nbConversionOfOneType;
	}
	
	private int getNbConversionEventCToA(Vector<Conversion> conversions) {
		int nbConversionEventCToA = 0;
		String previousTypeConversion = null;
		Boolean eventCToA = null;
		Iterator<Conversion> itConversion = conversions.iterator();
		while (itConversion.hasNext()) {
			Conversion conversion = itConversion.next();
			if (previousTypeConversion == null) {
				eventCToA = conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A);
			}
			else {
				if (!eventCToA) {
					eventCToA = (previousTypeConversion.equals(Main.TYPE_CONVERSION_HOMEO_SNP) && conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A));
				}
				else {
					if (conversion.getType().equals(Main.TYPE_CONVERSION_HOMEO_SNP)) {
						++nbConversionEventCToA;
						eventCToA = false;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C)) {
						eventCToA = false;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A)) {
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_A)) {
						eventCToA = false;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_C)) {
						eventCToA = false;
					}
				}
			}
			previousTypeConversion = conversion.getType();
		}
		if ((eventCToA != null) && eventCToA) {
			++nbConversionEventCToA;
		}
		return nbConversionEventCToA;
	}

	private int getNbConversionEventAToC(Vector<Conversion> conversions) {
		int nbConversionEventAToC = 0;
		String previousTypeConversion = null;
		Boolean eventAToC = null;
		Iterator<Conversion> itConversion = conversions.iterator();
		while (itConversion.hasNext()) {
			Conversion conversion = itConversion.next();
			if (previousTypeConversion == null) {
				eventAToC = conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C);
			}
			else {
				if (!eventAToC) {
					eventAToC = (previousTypeConversion.equals(Main.TYPE_CONVERSION_HOMEO_SNP) && conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C));
				}
				else {
					if (conversion.getType().equals(Main.TYPE_CONVERSION_HOMEO_SNP)) {
						++nbConversionEventAToC;
						eventAToC = false;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C)) {
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A)) {
						eventAToC = false;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_A)) {
						eventAToC = false;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_C)) {
						eventAToC = false;
					}
				}
			}
			previousTypeConversion = conversion.getType();
		}
		if ((eventAToC != null) && eventAToC) {
			++nbConversionEventAToC;
		}
		return nbConversionEventAToC;
	}
	
	private String getNbBorderEventCToA(Vector<Conversion> conversions) {
		String nbBorderEventCToA = "";
		String previousTypeConversion = null;
		Boolean eventCToA = null;
		Boolean isBorderLeft = null;
		Iterator<Conversion> itConversion = conversions.iterator();
		while (itConversion.hasNext()) {
			Conversion conversion = itConversion.next();
			if (previousTypeConversion == null) {
				eventCToA = conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A);
				isBorderLeft = (eventCToA?false:null);
			}
			else {
				if (!eventCToA) {
					eventCToA = (previousTypeConversion.equals(Main.TYPE_CONVERSION_HOMEO_SNP) && conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A));
					isBorderLeft = (eventCToA?true:null);
				}
				else {
					if (conversion.getType().equals(Main.TYPE_CONVERSION_HOMEO_SNP)) {
						nbBorderEventCToA = nbBorderEventCToA + (nbBorderEventCToA.equals("")?"":";") + (isBorderLeft?"2":"1");
						eventCToA = false;
						isBorderLeft = null;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C)) {
						eventCToA = false;
						isBorderLeft = null;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A)) {
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_A)) {
						eventCToA = false;
						isBorderLeft = null;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_C)) {
						eventCToA = false;
						isBorderLeft = null;
					}
				}
			}
			previousTypeConversion = conversion.getType();
		}
		if ((eventCToA != null) && eventCToA) {
			nbBorderEventCToA = nbBorderEventCToA + (nbBorderEventCToA.equals("")?"":";") + (isBorderLeft?"1":"0");
		}
		return nbBorderEventCToA;
	}
	
	private String getNbBorderEventAToC(Vector<Conversion> conversions) {
		String nbBorderEventAToC = "";
		String previousTypeConversion = null;
		Boolean eventAToC = null;
		Boolean isBorderLeft = null;
		Iterator<Conversion> itConversion = conversions.iterator();
		while (itConversion.hasNext()) {
			Conversion conversion = itConversion.next();
			if (previousTypeConversion == null) {
				eventAToC = conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C);
				isBorderLeft = (eventAToC?false:null);
			}
			else {
				if (!eventAToC) {
					eventAToC = (previousTypeConversion.equals(Main.TYPE_CONVERSION_HOMEO_SNP) && conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C));
					isBorderLeft = (eventAToC?true:null);
				}
				else {
					if (conversion.getType().equals(Main.TYPE_CONVERSION_HOMEO_SNP)) {
						nbBorderEventAToC = nbBorderEventAToC + (nbBorderEventAToC.equals("")?"":";") + (isBorderLeft?"2":"1");
						eventAToC = false;
						isBorderLeft = null;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C)) {
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A)) {
						eventAToC = false;
						isBorderLeft = null;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_A)) {
						eventAToC = false;
						isBorderLeft = null;
					}
					else if (conversion.getType().equals(Main.TYPE_CONVERSION_AUTAPOMORPHY_C)) {
						eventAToC = false;
						isBorderLeft = null;
					}
				}
			}
			previousTypeConversion = conversion.getType();
		}
		if ((eventAToC != null) && eventAToC) {
			nbBorderEventAToC = nbBorderEventAToC + (nbBorderEventAToC.equals("")?"":";") + (isBorderLeft?"1":"0");
		}
		return nbBorderEventAToC;
	}
	
	public void write(FileSequenceFasta fileSequenceFasta, String nameFileDataSequenceFasta) {
		Vector<Conversion> conversionDarmors = new Vector<Conversion>();

		String sequenceFastaBOleracea1 = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_B_OLERACEA_1).getSequence();
		String sequenceFastaBOleracea2 = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_B_OLERACEA_2).getSequence();
		String sequenceFastaBOleracea3 = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_B_OLERACEA_3).getSequence();
		String sequenceFastaBRapa1 = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_B_RAPA_1).getSequence();
		String sequenceFastaBRapa2 = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_B_RAPA_2).getSequence();
		String sequenceFastaBRapa3 = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_B_RAPA_3).getSequence();
		String sequenceFastaDarmorCGenome = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_DARMOR_C_GENOME).getSequence();
		String sequenceFastaDarmorAGenome = fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_DARMOR_A_GENOME).getSequence();
		for (int position = 1; position <= sequenceFastaBOleracea1.length(); position++) {
			if (
				(
					(sequenceFastaBOleracea1.charAt(position-1) == 'A') ||
					(sequenceFastaBOleracea1.charAt(position-1) == 'T') ||
					(sequenceFastaBOleracea1.charAt(position-1) == 'C') ||
					(sequenceFastaBOleracea1.charAt(position-1) == 'G')
				) &&
				(sequenceFastaBOleracea2.charAt(position-1) == sequenceFastaBOleracea1.charAt(position-1)) &&
				(sequenceFastaBOleracea3.charAt(position-1) == sequenceFastaBOleracea1.charAt(position-1)) &&
				(
					(sequenceFastaBRapa1.charAt(position-1) == 'A') ||
					(sequenceFastaBRapa1.charAt(position-1) == 'T') ||
					(sequenceFastaBRapa1.charAt(position-1) == 'C') ||
					(sequenceFastaBRapa1.charAt(position-1) == 'G')
				) &&
				(sequenceFastaBRapa2.charAt(position-1) == sequenceFastaBRapa1.charAt(position-1)) &&
				(sequenceFastaBRapa3.charAt(position-1) == sequenceFastaBRapa1.charAt(position-1))
			) {
				/*
					HomeoSNP											: (Bo != Bra AND BnapC = Bo AND BnapA = Bra)
					Gene conversion (A-->C)								: (Bo != Bra AND BnapC = BnapA = Bo)
					Gene conversion (C-->A)								: (Bo != Bra AND BnapC = BnapA = Bra)
					Autapomorphy A										: (Bo != Bra AND BnapC != BnapA AND BnapC = Bo AND BnapA != Bra)
					Autapomorphy C										: (Bo != Bra AND BnapC != BnapA AND BnapC != Bo AND BnapA = Bra)
				*/
				if (
						(
							(sequenceFastaDarmorCGenome.charAt(position-1) == 'A') ||
							(sequenceFastaDarmorCGenome.charAt(position-1) == 'T') ||
							(sequenceFastaDarmorCGenome.charAt(position-1) == 'C') ||
							(sequenceFastaDarmorCGenome.charAt(position-1) == 'G')
						) &&
						(
							(sequenceFastaDarmorAGenome.charAt(position-1) == 'A') ||
							(sequenceFastaDarmorAGenome.charAt(position-1) == 'T') ||
							(sequenceFastaDarmorAGenome.charAt(position-1) == 'C') ||
							(sequenceFastaDarmorAGenome.charAt(position-1) == 'G')
						)
					) {
					if (
						(sequenceFastaBOleracea1.charAt(position-1) != sequenceFastaBRapa1.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) == sequenceFastaBOleracea1.charAt(position-1)) &&
						(sequenceFastaDarmorAGenome.charAt(position-1) == sequenceFastaBRapa1.charAt(position-1))
					) {
						conversionDarmors.add(new Conversion(Main.TYPE_CONVERSION_HOMEO_SNP, position));
						/*if (stringBuffer.length() != 0) {
							stringBuffer.append(lineSeparator);
						}
						stringBuffer.append(nameFileDataSequenceFasta.substring(0, nameFileDataSequenceFasta.indexOf("-")) + SEPARATEUR + position + SEPARATEUR + Main.TYPE_CONVERSION_HOMEO_SNP + SEPARATEUR + "Darmor");*/
					}
					/*else */if (
						(sequenceFastaBOleracea1.charAt(position-1) != sequenceFastaBRapa1.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) == sequenceFastaDarmorAGenome.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) == sequenceFastaBOleracea1.charAt(position-1))
					) {
						conversionDarmors.add(new Conversion(Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C, position));
						/*if (stringBuffer.length() != 0) {
							stringBuffer.append(lineSeparator);
						}
						stringBuffer.append(nameFileDataSequenceFasta.substring(0, nameFileDataSequenceFasta.indexOf("-")) + SEPARATEUR + position + SEPARATEUR + Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C + SEPARATEUR + "Darmor");*/
					}
					/*else */if (
						(sequenceFastaBOleracea1.charAt(position-1) != sequenceFastaBRapa1.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) == sequenceFastaDarmorAGenome.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) == sequenceFastaBRapa1.charAt(position-1))
					) {
						conversionDarmors.add(new Conversion(Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A, position));
						/*if (stringBuffer.length() != 0) {
							stringBuffer.append(lineSeparator);
						}
						stringBuffer.append(nameFileDataSequenceFasta.substring(0, nameFileDataSequenceFasta.indexOf("-")) + SEPARATEUR + position + SEPARATEUR + Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A + SEPARATEUR + "Darmor");*/
					}
					/*else */if (
						(sequenceFastaBOleracea1.charAt(position-1) != sequenceFastaBRapa1.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) != sequenceFastaDarmorAGenome.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) == sequenceFastaBOleracea1.charAt(position-1)) &&
						(sequenceFastaDarmorAGenome.charAt(position-1) != sequenceFastaBRapa1.charAt(position-1))
					) {
						conversionDarmors.add(new Conversion(Main.TYPE_CONVERSION_AUTAPOMORPHY_A, position));
						/*if (stringBuffer.length() != 0) {
							stringBuffer.append(lineSeparator);
						}
						stringBuffer.append(nameFileDataSequenceFasta.substring(0, nameFileDataSequenceFasta.indexOf("-")) + SEPARATEUR + position + SEPARATEUR + Main.TYPE_CONVERSION_AUTAPOMORPHY_A + SEPARATEUR + "Darmor");*/
					}
					/*else */if (
						(sequenceFastaBOleracea1.charAt(position-1) != sequenceFastaBRapa1.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) != sequenceFastaDarmorAGenome.charAt(position-1)) &&
						(sequenceFastaDarmorCGenome.charAt(position-1) != sequenceFastaBOleracea1.charAt(position-1)) &&
						(sequenceFastaDarmorAGenome.charAt(position-1) == sequenceFastaBRapa1.charAt(position-1))
					) {
						conversionDarmors.add(new Conversion(Main.TYPE_CONVERSION_AUTAPOMORPHY_C, position));
						/*if (stringBuffer.length() != 0) {
							stringBuffer.append(lineSeparator);
						}
						stringBuffer.append(nameFileDataSequenceFasta.substring(0, nameFileDataSequenceFasta.indexOf("-")) + SEPARATEUR + position + SEPARATEUR + Main.TYPE_CONVERSION_AUTAPOMORPHY_C + SEPARATEUR + "Darmor");*/
					}
				}
			}
		}
		
		if (stringBuffer.length() != 0) {
			stringBuffer.append(lineSeparator);
		}
		stringBuffer.append(nameFileDataSequenceFasta.substring(0, nameFileDataSequenceFasta.indexOf("-")) + SEPARATEUR + "Darmor" + SEPARATEUR + conversionDarmors.size() + SEPARATEUR + getNbConversion(conversionDarmors, Main.TYPE_CONVERSION_HOMEO_SNP) + SEPARATEUR + getNbConversion(conversionDarmors, Main.TYPE_CONVERSION_AUTAPOMORPHY_A) + SEPARATEUR + getNbConversion(conversionDarmors, Main.TYPE_CONVERSION_AUTAPOMORPHY_C) + SEPARATEUR + getNbConversion(conversionDarmors, Main.TYPE_CONVERSION_GENE_CONVERSION_C_TO_A) + SEPARATEUR + getNbConversion(conversionDarmors, Main.TYPE_CONVERSION_GENE_CONVERSION_A_TO_C) + SEPARATEUR + getNbConversionEventCToA(conversionDarmors) + SEPARATEUR + getNbBorderEventCToA(conversionDarmors) + SEPARATEUR + getNbConversionEventAToC(conversionDarmors) + SEPARATEUR + getNbBorderEventAToC(conversionDarmors) + SEPARATEUR + fileSequenceFasta.getSequenceFastaByGenomes().get(Main.GENOME_DARMOR_C_GENOME).getSequence().length());
	}
	
	public byte[] getBytes() {
		stringBuffer.append(lineSeparator);
		return stringBuffer.toString().getBytes();
	}
}