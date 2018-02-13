import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {
	public static final int GENOME_B_OLERACEA_1												=	0;
	public static final int GENOME_B_OLERACEA_2												=	1;
	public static final int GENOME_B_OLERACEA_3												=	2;
	public static final int GENOME_B_RAPA_1													=	3;
	public static final int GENOME_B_RAPA_2													=	4;
	public static final int GENOME_B_RAPA_3													=	5;
	public static final int GENOME_DARMOR_C_GENOME											=	6;
	public static final int GENOME_DARMOR_A_GENOME											=	7;
	
	public static final String TYPE_CONVERSION_HOMEO_SNP									=	"HomeoSNP";
	public static final String TYPE_CONVERSION_GENE_CONVERSION_A_TO_C						=	"Gene conversion (A-->C)";
	public static final String TYPE_CONVERSION_GENE_CONVERSION_C_TO_A						=	"Gene conversion (C-->A)";
	public static final String TYPE_CONVERSION_AUTAPOMORPHY_A								=	"Autapomorphy A";
	public static final String TYPE_CONVERSION_AUTAPOMORPHY_C								=	"Autapomorphy C";

	// Input file : Fasta alignments only (no other file types)
	public static final String PATH_FILE_SEQUENCE_FASTA										=	"INPUT_PATH_FILE";
	// Output path file
	public static final String PATH_FILE_RESULT												=	"OUTPUT_PATH_FILE";
	// Output name file
	public static final String NAME_FILE_RESULT												=	"OUTPUT_NAME_FILE";

	public static void main(String[] args) {
		try {
			WriterFileResult writerFileResult = new WriterFileResult();

			File pathFileSequenceFasta = new File(PATH_FILE_SEQUENCE_FASTA);
			File[] fileSequenceFastas = pathFileSequenceFasta.listFiles();
			for (int i = 0; i < fileSequenceFastas.length; i++) {
				InputStream inputStream = new FileInputStream(fileSequenceFastas[i].getAbsolutePath());
				ReaderFileDataSequenceFasta readerFileDataSequenceFasta = new ReaderFileDataSequenceFasta();
				FileSequenceFasta fileSequenceFasta = readerFileDataSequenceFasta.read(inputStream, fileSequenceFastas[i].getName());
				writerFileResult.write(fileSequenceFasta, fileSequenceFastas[i].getName());
			}
			FileOutputStream fileResultOutputStream = new FileOutputStream(PATH_FILE_RESULT + NAME_FILE_RESULT);
			fileResultOutputStream.write(writerFileResult.getBytes());
			fileResultOutputStream.flush();
			fileResultOutputStream.close();
		} catch (ReaderFileDataSequenceFastaException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}