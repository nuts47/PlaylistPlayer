
public class VideoRecording extends Recording { //a child class of recording
	private final double framerate; //creates its specific instance value
	
	public VideoRecording() { //creates a nonparameterized constructor
		super();  //that sets all fields to a default value
		framerate = 0;
	}
	public VideoRecording(String songArtist, String songName, int songDuration, double songFramerate) { //parameterized constructor
		super(songArtist, songName, songDuration); //that calls the parent parameterized constructor
		if (songFramerate > 0) { //and if songframerate is not 0 or smaller
			framerate = songFramerate; //set framerate to it
		} else { //otherwise
			framerate = 0; //framerate is 0
		}
	}
	
	public double getFramerate() { //getter for framerate
		return framerate;
	}
	@Override //overriding the parent play method
	public void play() throws Unplayable{ //a method that simulates playing a recording
		try { //as long as there is a valid duration
			unplayable(); //checks to see if recording is 0
			int seconds = DURATION_IN_SECONDS % 60; //get seconds with the modulo operation
			int minutes = (DURATION_IN_SECONDS - seconds) / 60; //find minutes by subtracting seconds, then dividing by 60
			System.out.println("Now playing: " + ARTIST + " - " + NAME + " [" + minutes + "m" + seconds + "s] VIDEO | framerate: " + framerate + " fps]"); //print out the "now playing" message
			numberOfPlays++;
		} catch (Unplayable u) { //if the duration is not valid
			System.out.println(u.getMessage()); //throw an error
		}
	}
	public String toString() { //creates a toString method
		int seconds = DURATION_IN_SECONDS % 60; //gets seconds for the message                      |
		int minutes = (DURATION_IN_SECONDS - seconds) / 60; //gets minutes and returns that message \/
		return ARTIST + " - " + NAME + " [" + minutes + "m" + seconds + "s] [VIDEO | framerate: " + framerate + " fps] number of plays: " + numberOfPlays;
	}
}