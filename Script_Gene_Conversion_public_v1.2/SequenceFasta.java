public class SequenceFasta {
	private String identifiant;
	private String sequence;
	
	public SequenceFasta() {
	}
	
	public SequenceFasta(String identifiant, String sequence) {
		setIdentifiant(identifiant);
		setSequence(sequence);
	}
	
	public SequenceFasta(SequenceFasta sequenceFasta) {
		setIdentifiant(sequenceFasta.getIdentifiant());
		setSequence(sequenceFasta.getSequence());
	}
	
	public String toString() {
		return "[" + identifiant + "," + sequence + "]";
	}
	
	// getters - setters
	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	
	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	public Integer getLength() {
		return sequence.length();
	}
}