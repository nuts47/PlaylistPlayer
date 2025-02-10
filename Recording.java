
public abstract class Recording implements Playable, Comparable<Recording> {
	protected final String ARTIST; //creates an instance variable that will not be able to change once set with constructor
	protected final String NAME;   //creates an instance variable that will not be able to change once set with constructor
	protected final int DURATION_IN_SECONDS; //creates an instance variable that will not be able to change once set with constructor
	protected int numberOfPlays = 0;
	
	public Recording() {    //creates a nonparameterized constructor
		ARTIST = "Uknown";  //that sets all fields to a default value
		NAME = "Uknown";
		DURATION_IN_SECONDS = 0;
	}
	public Recording(String artist, String name, int duration) { //creates a parameterized constructor
		if (artist != null && name != null && duration > 0) {    //that makes sure all passed values are acceptable
			ARTIST = artist;  //then sets each value accordingly if valid
			NAME = name;
			DURATION_IN_SECONDS = duration;
		} else { //if not
			ARTIST = "Uknown";   //each field gets set to a default value
			NAME = "Uknown";
			DURATION_IN_SECONDS = 0;
		}
	}
	public String getARTIST() {  //a getter method for ARTIST
		return ARTIST;
	}
	public String getNAME() {    //a getter method for NAME
		return NAME;
	}
	public int getDURATION_IN_SECONDS() { //a getter method for DURATION_IN_SECONDS
		return DURATION_IN_SECONDS;
	}
	public int getNumberOfPlays() {
		return numberOfPlays;
	}
	@Override
	public void play() { //a method that simulates playing a recording
		try { //as long as there is a valid duration
			unplayable(); // checks to see if duration is 0
			int seconds = DURATION_IN_SECONDS % 60; //get seconds with the modulo operation
			int minutes = (DURATION_IN_SECONDS - seconds) / 60; //find minutes by subtracting seconds, then dividing by 60
			System.out.println("Now playing: " + ARTIST + " - " + NAME + " [" + minutes + "m" + seconds + "s]"); //print out the "now playing" message
		} catch (Unplayable u) { //if the duration is not valid
			System.out.println(u.getMessage()); //throw an error
		}
	}
	public String toString() { //a method that returns the play() message - now playing
		int seconds = DURATION_IN_SECONDS % 60;
		int minutes = (DURATION_IN_SECONDS - seconds) / 60;
		return ARTIST + " - " + NAME + " [" + minutes + "m" + seconds + "s]";
	}
	public void unplayable() throws Unplayable {
		if (DURATION_IN_SECONDS <= 0) {
			throw new Unplayable("Song " + NAME + " is unplayable as it has no duration");
		}
	}
	public int compareTo(Recording otherRecording) {
		if (otherRecording == null) return 0;
		return Integer.compare(numberOfPlays, otherRecording.getNumberOfPlays());
	}
}
