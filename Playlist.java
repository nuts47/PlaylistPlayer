import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner; //imports the Scanner class
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;

public class Playlist implements Playable, Comparable<Playlist> {
	String name;                  //next three lines create mutable instance variables
	int numberOfRecordings = 0;
	int durationInSeconds = 0;
	int numberOfPlays = 0;
	ArrayList<Recording> recordingList = new ArrayList<Recording>();    //creates an instance variable that will hold all objects of class Recording
	
	public Playlist() {           //non-parameterized constructor
		name = "uknown";          //that will set name and size to a default value
	}
	public Playlist(String nameInput) { //parameterized constructor
		if (nameInput != null) { //that checks if the values entered are legal
			name = nameInput;          //and sets name and size if they are
		} else { //if they aren't legal
			name = "uknown"; //same thing as the nonparameterized constructor
		}
	}
	
	public String getPlaylistName() { //getter method for name
		return name;
	}	
	public int getNumberOfRecordings() { //getter method for numberOfRecordings
		return numberOfRecordings;
	}
	public int getDurationInSeconds() { //getter method for durationInSeconds
		return durationInSeconds;
	}	
	public int getNumberOfPlays() {
		return numberOfPlays;
	}
	public ArrayList<Recording> getRecordingList() { //getter method for recordingList
		return recordingList;
	}	
	public void setName() { //mutator method for name
		Scanner myScanner = new Scanner(System.in); //creates a Scanner
		System.out.println("What would you like the playlist to be called?"); //asks the user for the name
		String tempName = myScanner.nextLine(); //stores what they enter
		while(tempName == null) { //checks to see if the input was valid
			System.out.println("The name of the playlist cannot be a null value, try again");
			tempName = myScanner.nextLine(); //and prompts the user until it gets a valid value
		}
		name = tempName; //sets the instance variable name to the user's input
		myScanner.close(); //closes the scanner to conserve memory
	}	
	public boolean add(Recording newRecording) { //method to create a new recording object and add it to recordingList, and return true if successful
		if (newRecording.getARTIST() != null && newRecording.getNAME() != null && newRecording.getDURATION_IN_SECONDS() > 0) {
			recordingList.add(newRecording);
			numberOfRecordings++; //increment numberOfRecordings
			durationInSeconds = durationInSeconds + newRecording.getDURATION_IN_SECONDS(); //add total duration of song to playlist
			return true; //and return true
			} else { //if at least one variable was not valid
			if (newRecording.getNAME() == null) { //check each variable, and print
				System.out.print("ERROR: the name of the song was a null value");
			}
			if (newRecording.getARTIST() == null) { //the corresponding error
				System.out.print("ERROR: the Artist's name was a null value");
			}
			if (newRecording.getDURATION_IN_SECONDS() <= 0) {
				System.out.print("ERROR: the duration of the song was 0 or smaller");
			}
			return false; //and return false
		}
	}
	public boolean remove(String name) {
		if (numberOfRecordings != 0) {
			for (Recording recording : recordingList) {
				if (recording.getNAME().equals(name)) {
					durationInSeconds -= recording.getDURATION_IN_SECONDS();
					recordingList.remove(recording);
					numberOfRecordings--;
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	public boolean remove(int index) {
		if (numberOfRecordings != 0) {
			if (index < numberOfRecordings) {
				durationInSeconds -= recordingList.get(index).getDURATION_IN_SECONDS();
				recordingList.remove(index);
				numberOfRecordings--;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	@Override
	public void play() throws Unplayable { //method that will simulate playing an album
		if (numberOfRecordings != 0) { //as long as there are recordings in the list
			try {
				if (durationInSeconds == 0) throw new Unplayable();
				System.out.println("Now playing playlist " + name); //print the now playing header
				int seconds = durationInSeconds % 60; //find seconds using total duration and the modulo operator
				int minutes = (durationInSeconds - seconds) / 60; //find minutes by subtracting seconds from the total and dividing by 60
				System.out.println("Total duration: [" + minutes + "m" + seconds + "s]"); //print the duration header
				for (int i = 0; i < numberOfRecordings; i++) {
					recordingList.get(i).play(); //and simulate playing each song
				}
				numberOfPlays++;
			} catch (Unplayable u) {
				System.out.println("Playlist has no duration, cannot be played");
			}
		} else { //if the list is empty
			System.out.println("ERROR: empty playlist"); //throw an error
		}
		System.out.println(""); //and space out the next call for easier reading
	}	
	public String toString() { //creates a method that will return a multi line string with playlist info and all recordings in it
		if (numberOfRecordings != 0) { //checks to make sure that the playlist is not empty
			int seconds = durationInSeconds % 60; //finds seconds and minutes like play()
			int minutes = (durationInSeconds - seconds) / 60;
			String playlistString = "Playlist: " + name + " [" + minutes + "m" + seconds + "s] total number of plays: " + numberOfPlays; //adds the header to the string
			String newLine = System.getProperty("line.separator"); //creates a separator to create a multi line string
			if (numberOfRecordings > 1) {
				Collections.sort(recordingList);
				Collections.reverse(recordingList);
			}
			for (int i = 0; i < numberOfRecordings; i++) {
				playlistString +=  newLine; //iterates through recording list and adds each recording on a new line
				playlistString += " " + i + " " + recordingList.get(i).toString();
			}
			playlistString = playlistString + newLine;
			return playlistString; //returns the string
		} else { //but if its empty
			System.out.println("This playlist has no songs"); //throw an error
			System.out.println(""); //space it from the next output
			return "Playlist: " + name + " has no duration"; //and return null.
		}
	}
	public void shuffle(int numberOfRecordingsToPlay) { //method that "shuffles" a set number of songs
		if (numberOfRecordings > 0) { //as long as the playlist has songs
			Recording[] shuffleRecordings = new Recording[numberOfRecordingsToPlay]; //create a new recording list of passed size
			for (int i = 0; i < numberOfRecordingsToPlay; i++) { //iterates passed number times
				Random myObj = new Random(); //creates a random object
				shuffleRecordings[i] = recordingList.get(myObj.nextInt(numberOfRecordings)); //sets i index to a random song
				shuffleRecordings[i].play(); //and plays it
			}
		} else { //otherwise
			System.out.println("ERROR: no recordings to play"); //throw and error
		}
	}
	public boolean load (String fileName) { //creates a method that will load songs into a playlist from a file
		try { //beginning of try-catch for the FileNotFoundException
			File inputFile = new File(fileName); //creates a new file object from the passed name
			Scanner myScanner = new Scanner(inputFile); //creates a new scanner that will read the file
			String[] fileContentsLong = new String[100]; //creates a long list that will hold each line of the file
			int i = 0; //create the counter i
			while (myScanner.hasNextLine()) { //iterates through the file
				fileContentsLong[i] = myScanner.nextLine(); //and stores each line in the list
				i++; //increments i
			}
			myScanner.close(); //closes scanner for resources
			String[] fileContents = new String[i]; //creates a new list that is i length
			System.arraycopy(fileContentsLong, 0, fileContents, 0, i); //and copy the old list into the new one
			fileContentsLong = null; //set the old list to null for resources
			for (int x = 0; x < fileContents.length; x++) { //iterate through fileContents
				try { //beginning of try-catch for the NumberMismatchException
					String[] lineContents = fileContents[x].split(","); //create a list for each line and split the string for each line at commas
					String type = lineContents[0]; //set type to the first index
					String artist = lineContents[1]; //artist to the second
					String name = lineContents[2]; //name to the third
					int duration = Integer.parseInt(lineContents[3]); //duration to an Integer interpretation of the fourth
					durationInSeconds += duration;
					if (type.equals("A")) { //if type is A
						long bitrate = Long.parseLong(lineContents[4]); //set bitrate to a Long interpretation of the fifth
						recordingList.add(new AudioRecording(artist, name, duration, bitrate)); //and create an audio recording
					} else if (type.equals("V")) { //if type is B
						double framerate = Double.parseDouble(lineContents[4]); //set framerate to a Double interpretation of the fifth
						recordingList.add(new VideoRecording(artist, name, duration, framerate)); //and create a video recording
					}
					numberOfRecordings++; //increments instance counter
				} catch (NumberFormatException nfe) { //catches NumberFormatException
					System.out.println("ERROR: NumberFormatException, song rejected " + fileContents[x]); //throw an error that shows which song threw it
				}
			}
			return true;
		} catch (FileNotFoundException fnfe) { //catches FileNotFoundException
			System.out.println("ERROR: File " + fileName + " not found"); //throws an error and tells which file threw it
			return false;
		}
	}
	public int compareTo(Playlist otherPlaylist) {
		if (otherPlaylist == null) return 0;
		return Integer.compare(numberOfPlays, otherPlaylist.getNumberOfPlays());
	}
}
