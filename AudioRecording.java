
public class AudioRecording extends Recording{ //a child class of recording
	private final double bitrate; //creates its specific instance value
	
	public AudioRecording() { //creates a nonparameterized constructor
		super();  //that sets all fields to a default value
		bitrate = 0;
	}
	public AudioRecording(String songArtist, String songName, int songDuration, long songbitrate) { //parameterized constructor
		super(songArtist, songName, songDuration); //that calls the parent parameterized constructor
		if (songbitrate > 0) { //and if songbitrate is not 0 or smaller
			bitrate = songbitrate; //set bitrate to it
		} else { //otherwise
			bitrate = 0; //bitrate is zero
		}
	}
	
	public double getBitrate() { //getter for bitrate
		return bitrate;
	}
	@Override //overriding the parent play method
	public void play() throws Unplayable{ //a method that simulates playing a recording
		try { //as long as there is a valid duration
			unplayable(); //checks to see if duration is 0
			int seconds = DURATION_IN_SECONDS % 60; //get seconds with the modulo operation
			int minutes = (DURATION_IN_SECONDS - seconds) / 60; //find minutes by subtracting seconds, then dividing by 60
			System.out.println("Now playing: " + ARTIST + " - " + NAME + " [" + minutes + "m" + seconds + "s] [AUDIO | bitrate: " + bitrate + " kbps]"); //print out the "now playing" message
			numberOfPlays++;
		} catch (Unplayable u) { //if the duration is not valid
			System.out.println(u.getMessage()); //throw an error
		}
	}
	public String toString() { //creates a toString method
		int seconds = DURATION_IN_SECONDS % 60; //gets seconds for the message                      |
		int minutes = (DURATION_IN_SECONDS - seconds) / 60; //gets minutes and returns that message \/
		return ARTIST + " - " + NAME + " [" + minutes + "m" + seconds + "s] [AUDIO | bitrate: " + bitrate + " kbps] number of plays: " + numberOfPlays;
	}
}