import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;

public class ReaderFileDataSequenceFasta {
	private BufferedReader reader;
	private int rowFile;
	private String dataLine;
	
	public ReaderFileDataSequenceFasta() {
	}
	
	private void readLine() throws IOException {
		dataLine = null;
		if (reader.ready()) {
			++rowFile;
			dataLine = reader.readLine();
			while (dataLine.startsWith(" ")) {
				dataLine = dataLine.substring(1, dataLine.length());
			}
			while (dataLine.endsWith(" ")) {
				dataLine = dataLine.substring(0, dataLine.length()-1);
			}
		}
	}

	private String readIdentifiant() throws IOException, ReaderFileDataSequenceFastaException {
		if (dataLine.equals("")) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("L'identifiant n'est pas renseigné à la " + (rowFile+1) + (rowFile == 0?"ère":"ème") + " ligne !");
		}
		if (!dataLine.startsWith(">")) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("La " + (rowFile+1) + (rowFile == 0?"ère":"ème") + " ligne ne commence pas par le caractère '>' !");
		}
		String identifiant = dataLine.substring(1);
		if (identifiant.equals("")) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("L'identifiant n'est pas renseigné à la " + (rowFile+1) + (rowFile == 0?"ère":"ème") + " ligne !");
		}
		/*if (identifiant.indexOf(" ") != -1) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("L'identifiant '" + identifiant + "' contient un ou plusieurs caractères espace à la " + (rowFile+1) + (rowFile == 0?"ère":"ème") + " ligne !");
		}*/
		return identifiant;
	}
	
	private String readSequence(String identifiant) throws IOException, ReaderFileDataSequenceFastaException {
		if (!reader.ready()) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("Le dernier identifiant n'a pas de séquence !");
		}
		String sequence = null;
		readLine();
		//System.out.println("Ligne n°" + (rowFile+1));
		if (dataLine.equals("")) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("La séquence n'est pas renseignée à la " + (rowFile+1) + "ème ligne !");
		}
		if (dataLine.startsWith(">")) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("La séquence de l'identifiant '" + identifiant + "' est manquante à la " + (rowFile+1) + "ème ligne !");
		}
		if (dataLine.indexOf(" ") != -1) {
			reader.close();
			throw new ReaderFileDataSequenceFastaException("La séquence '" + dataLine + "' contient un ou plusieurs caractères espace à la " + (rowFile+1) + "ème ligne !");
		}
		sequence = dataLine;
		readLine();
		return sequence;
	}
	
	public FileSequenceFasta read(InputStream inputStream) throws ReaderFileDataSequenceFastaException {
		return read(inputStream, null);
	}
	
	public FileSequenceFasta read(InputStream inputStream, String nameFileDataSequenceFasta) throws ReaderFileDataSequenceFastaException {
		try {
			HashMap<Integer, SequenceFasta> sequenceFastaByGenomes = new HashMap<Integer, SequenceFasta>();
			Vector<String> identifiants = new Vector<String>();
			reader = new BufferedReader(new InputStreamReader(inputStream));
			readLine();
			rowFile = 0;
			while (dataLine != null) {
				//System.out.println("Ligne n°" + (rowFile+1));
				String identifiant = readIdentifiant();
				if (identifiants.contains(identifiant)) {
					reader.close();
					throw new ReaderFileDataSequenceFastaException("L'identifiant de la séquence '" + identifiant + "' est présent plusieurs fois dans le fichier '" + nameFileDataSequenceFasta + "' !");
				}
				SequenceFasta sequenceFasta = new SequenceFasta();
				sequenceFasta.setIdentifiant(identifiant);
				sequenceFasta.setSequence(readSequence(identifiant));
				if ((sequenceFastaByGenomes.size() >= 1) && (sequenceFasta.getLength().intValue() != sequenceFastaByGenomes.get(0).getLength().intValue())) {
					reader.close();
					throw new ReaderFileDataSequenceFastaException("Les séquences Fasta n'ont pas toutes la même taille dans le fichier '" + nameFileDataSequenceFasta + "' !");
				}
				switch (sequenceFastaByGenomes.size()) {
					case 0 :
						sequenceFastaByGenomes.put(Main.GENOME_B_OLERACEA_1, sequenceFasta);
						break;
					case 1 :
						sequenceFastaByGenomes.put(Main.GENOME_B_OLERACEA_2, sequenceFasta);
						break;
					case 2 :
						sequenceFastaByGenomes.put(Main.GENOME_B_OLERACEA_3, sequenceFasta);
						break;
					case 3 :
						sequenceFastaByGenomes.put(Main.GENOME_B_RAPA_1, sequenceFasta);
						break;
					case 4 :
						sequenceFastaByGenomes.put(Main.GENOME_B_RAPA_2, sequenceFasta);
						break;
					case 5 :
						sequenceFastaByGenomes.put(Main.GENOME_B_RAPA_3, sequenceFasta);
						break;
					case 6 :
						sequenceFastaByGenomes.put(Main.GENOME_DARMOR_C_GENOME, sequenceFasta);
						break;
					case 7 :
						sequenceFastaByGenomes.put(Main.GENOME_DARMOR_A_GENOME, sequenceFasta);
						break;
					default :
						break;
				}
				identifiants.add(identifiant);
			}
			reader.close();
			
			if (identifiants.size() != 8) {
				reader.close();
				throw new ReaderFileDataSequenceFastaException("Il y a " + identifiants.size() + " séquence(s) Fasta au lieu de 8 dans le fichier '" + nameFileDataSequenceFasta + "' !");
			}
			/*if (sequenceFastaByGenomes == null || sequenceFastaByGenomes.size() == 0) {
				throw new ReaderFileDataSequenceFastaException("Le fichier est vide !");
			}*/

			return new FileSequenceFasta(sequenceFastaByGenomes);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}