import java.util.HashMap;

public class FileSequenceFasta {
	private HashMap<Integer, SequenceFasta> sequenceFastaByGenomes;
	
	public FileSequenceFasta() {
	}
	
	public FileSequenceFasta(HashMap<Integer, SequenceFasta> sequenceFastaByGenomes) {
		setSequenceFastaByGenomes(sequenceFastaByGenomes);
	}
	
	public FileSequenceFasta(FileSequenceFasta fileSequenceFasta) {
		setSequenceFastaByGenomes(fileSequenceFasta.getSequenceFastaByGenomes());
	}
	
	public String toString() {
		return "[" + sequenceFastaByGenomes + "]";
	}

	// getters - setters
	public HashMap<Integer, SequenceFasta> getSequenceFastaByGenomes() {
		return sequenceFastaByGenomes;
	}

	public void setSequenceFastaByGenomes(HashMap<Integer, SequenceFasta> sequenceFastaByGenomes) {
		this.sequenceFastaByGenomes = sequenceFastaByGenomes;
	}
}