import java.io.File;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class User implements Playable {
	private final String NAME;
	private ArrayList<Playlist> userPlaylists = new ArrayList<Playlist>();
	private int numberOfPlaylists = 0;
	private static AtomicInteger ID_GENERATOR = new AtomicInteger();
	private int userID;
	
	public User() {
		NAME = "Unknown";
		userID = ID_GENERATOR.getAndIncrement();
	}
	public User(String possibleName) {
		if (possibleName != null) {
			NAME = possibleName;
		} else {
			NAME = "Unknown";
		}
		userID = ID_GENERATOR.getAndIncrement();
	}
	
	public String getNAME() {
		return NAME;
	}
	public ArrayList<Playlist> getUserPlaylists() {
		return userPlaylists;
	}
	public int getNumberOfPlaylists() {
		return numberOfPlaylists;
	}
	public int getUserID() {
		return userID;
	}
	public boolean addPlaylist(String playlistName, int playlistSize) {
		if (!playlistName.equals(null)) {
			userPlaylists.add(new Playlist(playlistName));
			numberOfPlaylists++;
			return true;
		} else {
			return false;
		}
	}
	public boolean addPlaylist(String filename) {
		try { //beginning of try-catch for the FileNotFoundException
			File inputFile = new File(filename); //creates a new file object from the passed name
			Scanner myScanner = new Scanner(inputFile); //creates a new scanner that will read the file
			String name = myScanner.nextLine();
			myScanner.close();
			if (name != null) {
				userPlaylists.add(new Playlist(name));
			} else {
				System.out.println("Either the name of the playlist or size of it was corrupted, creating a default playlist");
				userPlaylists.add(new Playlist());
			}
			userPlaylists.get(numberOfPlaylists).load(filename);
			numberOfPlaylists++;
			return true;
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: File Not Found");
			return false;
		}
	}
	public boolean addPlaylist(Playlist otherPlaylist) {
		userPlaylists.add(otherPlaylist);
		numberOfPlaylists++;
		return true;
	}
	public boolean removePlaylist(String name) {
		for (Playlist playlist: userPlaylists) {
	        if (playlist.getPlaylistName().equals(name)) {
	            userPlaylists.remove(userPlaylists.indexOf(playlist));
	            numberOfPlaylists--;
	            return true;
	        }
	    }
		return false;
	}
	public boolean removePlaylist(int i) {
		if (i < userPlaylists.size() && i >= 0) {
			userPlaylists.remove(i);
			numberOfPlaylists--;
			return true;
		}
		return false;
	}
	public boolean writePlaylistToFile(Playlist targetPlaylist, String filename) {
		try {
			FileOutputStream fos = new FileOutputStream(filename, true); //sets name of file and to not overwrite
        	PrintWriter pw = new PrintWriter(fos); //sets up the print writer
        	String newLine = System.getProperty("line.separator");
        	int unwritable = 0;
        	ArrayList<Recording> songList = targetPlaylist.getRecordingList();
        	for (int i = 0; i < targetPlaylist.getNumberOfRecordings(); i++) {
        		if (songList.get(i).getClass() == AudioRecording.class) {
        			AudioRecording song = (AudioRecording) songList.get(i);
        			String durationInSeconds = Integer.toString(song.getDURATION_IN_SECONDS());
        			String bitrate = Double.toString(song.getBitrate());
        			pw.print("A,");
        			pw.print(song.getARTIST() + ',');
        			pw.print(song.getNAME() + ',');
        			pw.print(durationInSeconds + ',');
        			pw.print(bitrate + ',');
        			pw.print(newLine);
        		} else if (songList.get(i).getClass() == VideoRecording.class) {
        			VideoRecording song = (VideoRecording) songList.get(i);
        			String durationInSeconds = Integer.toString(song.getDURATION_IN_SECONDS());
        			String framerate = Double.toString(song.getFramerate());
        			pw.print("V,");
        			pw.print(song.getARTIST() + ',');
        			pw.print(song.getNAME() + ',');
        			pw.print(durationInSeconds + ',');
        			pw.print(framerate + ',');
        			pw.print(newLine);
        		} else {
        			unwritable++;
        		}
        	}
        	pw.close();
    		if (unwritable > 0) {
    			System.out.println(unwritable + " songs could not be written to the file");
    		}
    		return true;
		} catch (FileNotFoundException fnfe) {
			System.out.println("ERROR: File Not Found");
			return false;
		}
	}
	@Override
	public void play() throws Unplayable {
		if (numberOfPlaylists > 0) {
			System.out.println("Playling playlists under user " + NAME);
			for (Playlist playlist : userPlaylists) {
				try {
					int duration = playlist.getDurationInSeconds();
					if (duration == 0) throw new Unplayable();
					playlist.play();
				} catch (Unplayable u) {
					System.out.println("Playlist " + playlist.getPlaylistName() + " has no duration and therefore no songs");
				}
			}
		} else {
			System.out.println("You need to add playlists before playing them");
		}
	}
	public String toString() {
		String newLine = System.getProperty("line.separator");
		String playlists = "";
		int i = 0;
		if (numberOfPlaylists > 1) {
			Collections.sort(userPlaylists);
			Collections.reverse(userPlaylists);
		}
		for (Playlist playlist: userPlaylists) {
			playlists += " " + i + " " + playlist.getPlaylistName() + " | total number of plays: " + playlist.getNumberOfPlays() + newLine;
			i++;
		}
		return numberOfPlaylists + " Playlists owned by " + NAME + newLine + playlists;
	}
} 
