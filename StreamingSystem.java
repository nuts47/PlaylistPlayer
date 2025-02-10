import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.time.*;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

public class StreamingSystem {
	public static void menu() throws InputMismatchException {
		new UserDatabase();
		UserDatabase.addUser("New User");
		UserDatabase.getUsers().get(0).addPlaylist("New Playlist", 5);
		
		boolean exitted = false;
		boolean canceled = false;
		boolean isDone = false;
		boolean loggedOut = true;
		boolean userSelected = false;
		boolean secondUserSelected = false;
		boolean playlistSelected = false;
		boolean secondPlaylistSelected = false;
		int userIndex = -1;
		int secondUserIndex = -1;
		int playlistIndex = -1;
		int secondPlaylistIndex = -1;
		int userInputInt;
		int bitrate;
		double framerate;
		Scanner myScanner = new Scanner(System.in);
		String userInput;
		String name;
		String songType;
		ArrayList<User> users = UserDatabase.getUsers();
		Playlist selectedPlaylist;
		
		System.out.println("Welcome to the Streaming System!");
		while (!exitted) {
			System.out.println(UserDatabase.toString("Print stuff"));
			System.out.println("Please select an Option: Add User(A), Remove User(R), Select User(S), Exit(E)");
			userInput = myScanner.nextLine().toLowerCase();
			if (userInput.equals("a") || userInput.equals("add user")) {
				canceled = false;
				isDone = false;
				while (!canceled && !isDone) {
					System.out.println("Please enter a username or type \"c\" to cancel");
					name = myScanner.nextLine();
					if (name != null) {
						if (name.equals("c") || name.equals("cancel")) {
							canceled = true;
						} else {
							UserDatabase.addUser(name);
							canceled = true;
							isDone = true;
						}
					} else {
						System.out.println("That name was invalid, try again");
					}
				}
			} else if (userInput.equals("r") || userInput.equals("remove user")) {
				canceled = false;
				isDone = false;
				if (UserDatabase.getNumberOfUsers() == 1) {
					System.out.println("ERROR, if the user is removed, the database will be empty");
					isDone = true;
					canceled = true;
				}
				while (!canceled && !isDone) {
					System.out.println("Remove user based on Name(N) or ID(I)? Type \"c\" to cancel");
					userInput = myScanner.nextLine().toLowerCase();
					if (userInput.equals("n") || userInput.equals("name")) {
						canceled = false;
						while (!canceled) {
							System.out.println("Type the username that you want to remove. Type \"c\" to cancel");
							name = myScanner.nextLine();
							if (name.toLowerCase().equals("c") || name.toLowerCase().equals("cancel")) {
								canceled = true;
							} else if (UserDatabase.removeUser(name)) {
								canceled = true;
								isDone = true;
							} else {
								System.out.println("Please try again, invalid name");
							}
						}
						canceled = false;
					} else if (userInput.equals("i") || userInput.equals("id")) {
						while (!canceled) {
							System.out.println("Type the ID that you want to remove. Type -1 to cancel");
							try {
								userInputInt = myScanner.nextInt();
								myScanner.nextLine();
								if (userInputInt == -1) {
									canceled = true;
								} else if (userInputInt >= 0) {
									int i = 0;
									for (User user : users) {
										if (user.getUserID() == userInputInt) {
											UserDatabase.removeUser(i);
											isDone = true;
											canceled = true;
										}
										i++;
									}
								} else {
									System.out.println("Invalid index, try again");
								}
							} catch (InputMismatchException ime) {
								System.out.println("Please enter an integer...");
								myScanner.next();
							}
						}
						canceled = false;
					} else if (userInput.equals("c") || userInput.equals("cancel")) {
						canceled = true;
					} else {
						System.out.println("Uknown command, try again");
					}
				}
			} else if (userInput.equals("s") || userInput.equals("select user")) {
				canceled = false;
				isDone = false;
				while (!canceled && !isDone) {
					System.out.println("Select user based on Name(N) or ID(I)? Type \"c\" to cancel");
					userInput = myScanner.nextLine();
					if (userInput.equals("n") || userInput.equals("name")) {
						canceled = false;
						while (!canceled) {
							System.out.println("Type the username that you want to select. Type \"c\" to cancel");
							name = myScanner.nextLine();
							if (name.equals("c") || name.equals("cancel")) {
								canceled = true;
							} else if (!name.equals(null)){
								users = UserDatabase.getUsers();
								for (User user : users) {
									if (user.getNAME().equals(name)) {
										userIndex = users.indexOf(user);
										userSelected = true;
										canceled = true;
										isDone = true;
										loggedOut = false;
									}
								} 
							} else {
								System.out.println("That name was invalid, try again");
							}
						}
						canceled = false;
					} else if (userInput.equals("i") || userInput.equals("id")) {
						while (!canceled) {
							System.out.println("Type the user ID that you want to select. Type -1 to cancel");
							try {
								userInputInt = myScanner.nextInt();
								myScanner.nextLine();
								if (userInputInt >= 0) {
									int i = 0;
									for (User user : users) {
										if (user.getUserID() == userInputInt) {
											userIndex = i;
											userSelected = true;
											canceled = true;
											isDone = true;
											loggedOut = false;
										}
										i++;
									}
								} else if (userInputInt == -1) {
									canceled = true;
								} else {
									System.out.println("Invalid ID, try again");
								}
							} catch (InputMismatchException ime) {
								System.out.println("Please don't enter anything but an int");
								myScanner.next();
							}
						}
						canceled = false;
					} else if (userInput.equals("c") || userInput.equals("cancel")) {
						canceled = true;
					} else {
						System.out.println("Uknown command, try again");
					} if (userSelected) {
						while (!loggedOut) {
							System.out.println(users.get(userIndex).toString());
							System.out.println("Please select an Option: Add Playlist(A), Remove Playlist(R), Select Playlist(S), Write Playlist to a File(F), Log out(L)");
							userInput = myScanner.nextLine().toLowerCase();
							if (userInput.equals("a") || userInput.equals("add playlist")) {
								canceled = false;
								isDone = false;
								while (!canceled && !isDone) {
									System.out.println("Would you like to create an Empty Playlist(E) or add one from Another User(A). Type \"c\" to cancel");
									userInput = myScanner.nextLine().toLowerCase();
									if (userInput.equals("e") || userInput.equals("empty playlist")) {
										while (!canceled && !isDone) {
											System.out.println("Please enter the name of the playlist. Type \"c\" to cancel");
											name = myScanner.nextLine();
											if (name.toLowerCase().equals("c") || name.toLowerCase().equals("cancel")) {
												canceled = true;
											} else if (name != null && !name.toLowerCase().equals("c")) {
												int size = 5;
												users.get(userIndex).addPlaylist(name, size);
												canceled = true;
												isDone = true;
											} else {
												System.out.println("Invalid name, try again");
											}
										}
										canceled = false;
									} else if (userInput.equals("a") || userInput.equals("another user")) {
										while (!canceled && !isDone) {
											if (!secondUserSelected) {
												System.out.println(UserDatabase.toString("yes"));
												System.out.println("Please enter if you are selecting by Name(N) or ID(i). Type \"c\" to cancel");
												userInput = myScanner.nextLine();
											} if (userInput.equals("n") || userInput.equals("name")) {
												canceled = false;
												while (!canceled) {
													System.out.println("Type the user that you want to select. Type \"c\" to cancel");
													name = myScanner.nextLine();
													if (name.equals("c") || name.equals("cancel")) {
														canceled = true;
													} else if (name != null){
														users = UserDatabase.getUsers();
														for (User user : users) {
															if (user.getNAME().equals(name)) {
																secondUserIndex = users.indexOf(user);
																secondUserSelected = true;
																canceled = true;
																isDone = true;
															} 
														}
													} 
												}
												canceled = false;
											} else if (userInput.equals("i") || userInput.equals("id")) {
												while (!canceled) {
													System.out.println("Type the id that you want to select. Type -1 to cancel");
													try {
														userInputInt = myScanner.nextInt();
														myScanner.nextLine();
														if (userInputInt >= 0) {
															int i = 0;
															for (User user : users) {
																if (user.getUserID() == userInputInt) {
																	secondUserIndex = i;
																	secondUserSelected = true;
																	canceled = true;
																	isDone = true;
																}
																i++;
															}
														} else if (userInputInt == -1) {
															canceled = true;
														} else {
															System.out.println("Invalid id, try again");
														}
													} catch (InputMismatchException ime) {
														System.out.println("Please, no Strings, this program is fragile");
														myScanner.next();
													}
												}
												System.out.println("test");
												canceled = false;
											} else if (userInput.equals("c") || userInput.equals("cancel")) {
												canceled = true;
											} else {
												System.out.println("Unknown command");
											}
											canceled = false;
										} if (secondUserSelected) {
											isDone = false;
											if (!secondPlaylistSelected) {
												System.out.println(UserDatabase.getUsers().get(secondUserIndex).toString());
												System.out.println("Please enter if you are selecting by Name(N) or Index(I). Type \"c\" to cancel"); //gets here then skips scanner
												userInput = myScanner.nextLine();
												if (userInput.equals("n") || userInput.equals("name")) {
													canceled = false;
													while (!canceled && !isDone) {
														System.out.println("Type the playlist name that you want to select. Type \"c\" to cancel");
														name = myScanner.nextLine();
														if (name.equals("c") || name.equals("cancel")) {
															canceled = true;
														} else if (name != null){
															users = UserDatabase.getUsers();
															for (Playlist playlist : users.get(secondUserIndex).getUserPlaylists()) {
																if (playlist.getPlaylistName().equals(name)) {
																	secondPlaylistIndex = users.get(secondUserIndex).getUserPlaylists().indexOf(playlist);
																	secondPlaylistSelected = true;
																	canceled = true;
																	isDone = true;
																}
															}
														} 
													}
													canceled = false;
													} else if (userInput.equals("i") || userInput.equals("index")) {
														while (!canceled && !isDone) {
															System.out.println("Type the index that you want to select. Type -1 to cancel");
															try {
																userInputInt = myScanner.nextInt();
																myScanner.nextLine();
																if (userInputInt >= 0 && userInputInt < UserDatabase.getUsers().get(secondUserIndex).getUserPlaylists().size()) {
																	secondPlaylistIndex = userInputInt;
																	secondPlaylistSelected = true;
																	canceled = true;
																	isDone = true;
																} else if (userInputInt == -1) {
																	canceled = true;
																} else {
																	System.out.println("Invalid index, try again");
																}
															} catch (InputMismatchException ime) {
																System.out.println("You're testing me, aren't you?");
																myScanner.next();
															}
														}
												}
												canceled = false;
											} if (secondPlaylistSelected) {
												if (users.get(userIndex).addPlaylist(users.get(secondUserIndex).getUserPlaylists().get(secondPlaylistIndex))) {
													isDone = true;
													System.out.println("Successfully copied the playlist");
												} else {
													isDone = true;
													System.out.println("Failed to add playlist, will need to restart");
												}
												secondUserSelected = false;
												secondUserIndex = -1;
												secondPlaylistSelected = false;
												secondPlaylistIndex = -1;
											}
										}
									} else if (userInput.equals("c") || userInput.equals("cancel")) {
										canceled = true;
									} else {
										System.out.println("Uknown command, try again");
									}
								}
							} else if (userInput.equals("r") || userInput.equals("remove playlist")) {
								isDone = false;
								canceled = false;
								while (!canceled && !isDone) {
									System.out.println(UserDatabase.getUsers().get(userIndex).toString());
									System.out.println("Please select if you are removing by Name(N) or Index(I). Type \"c\" to cancel");
									userInput = myScanner.nextLine();
									if (userInput.equals("n") || userInput.equals("name")) {
										canceled = false;
										while (!canceled && !isDone) {
											System.out.println("Type the playlist name that you want to select. Type \"c\" to cancel");
											name = myScanner.nextLine();
											if (name.equals("c") || name.equals("cancel")) {
												canceled = true;
											} else if (name != null){
												users = UserDatabase.getUsers();
												for (Playlist playlist : users.get(userIndex).getUserPlaylists()) {
													if (playlist.getPlaylistName().equals(name)) {
														UserDatabase.getUsers().get(userIndex).removePlaylist(name);
														canceled = true;
														isDone = true;
														break;
													} 
												}
											} 
										}
										canceled = false;
										} else if (userInput.equals("i") || userInput.equals("index")) {
											while (!canceled && !isDone) {
												System.out.println("Type the index that you want to select. Type -1 to cancel");
												try {
													userInputInt = myScanner.nextInt();
													myScanner.nextLine();
													if (userInputInt >= 0 && userInputInt < UserDatabase.getUsers().get(userIndex).getUserPlaylists().size()) {
														UserDatabase.getUsers().get(userIndex).removePlaylist(userInputInt);
														canceled = true;
														isDone = true;
													} else if (userInputInt == -1) {
														canceled = true;
													} else {
														System.out.println("Invalid index, try again");
													}
												} catch (InputMismatchException ime) {
													System.out.println("Fun fact: an integer needs to be entered into this field");
													myScanner.next();
												}
											}
										} else if (userInput.equals("c") || userInput.equals("cancel")) {
											canceled = true;
										} else {
											System.out.println("Uknown command, try again");
										}
								}
							} else if (userInput.equals("s") || userInput.equals("select playlist")) {
								isDone = false;
								canceled = false;
								if (!playlistSelected) {
									System.out.println(UserDatabase.getUsers().get(userIndex).toString());
									System.out.println("Please enter if you are selecting by Name(N) or Index(I). Type \"c\" to cancel");
									userInput = myScanner.nextLine();
									if (userInput.equals("n") || userInput.equals("name")) {
										canceled = false;
										while (!canceled && !isDone) {
											System.out.println("Type the playlist name that you want to select. Type \"c\" to cancel");
											name = myScanner.nextLine();
											if (name.equals("c") || name.equals("cancel")) {
												canceled = true;
											} else if (name != null) {
												users = UserDatabase.getUsers();
												for (Playlist playlist : users.get(userIndex).getUserPlaylists()) {
													if (playlist.getPlaylistName().equals(name)) {
														playlistIndex = users.get(userIndex).getUserPlaylists().indexOf(playlist);
														playlistSelected = true;
														canceled = true;
														isDone = true;
													}
												}
											} 
										}
											canceled = false;
										} else if (userInput.equals("i") || userInput.equals("index")) {
											while (!canceled && !isDone) {
												System.out.println("Type the index that you want to select. Type -1 to cancel");
												try {
													userInputInt = myScanner.nextInt();
													myScanner.nextLine();
													if (userInputInt >= 0 && userInputInt < UserDatabase.getUsers().get(userIndex).getUserPlaylists().size()) {
														playlistIndex = userInputInt;
														playlistSelected = true;
														canceled = true;
														isDone = true;
													} else if (userInputInt == -1) {
														canceled = true;
													} else {
														System.out.println("Invalid index, try again");
													}
												} catch (InputMismatchException ime) {
													System.out.println("AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH... int please");
													myScanner.next();
												}
											}
										}
										canceled = false;
										} else if (userInput.equals("c") || userInput.equals("cancel")) {
											canceled = true;
										} else {
											System.out.println("Uknown command, try again");
								} if (playlistSelected) {
									isDone = false;
									canceled = false;
									selectedPlaylist = UserDatabase.getUsers().get(userIndex).getUserPlaylists().get(playlistIndex);
									while (playlistSelected) {
										System.out.println(selectedPlaylist.toString());
										System.out.println("Please select an Option: Add Song(A), Remove Song(R), Play Playlist(P), Play Song(S), Shuffle Playlist(H), Exit(E)");
										userInput = myScanner.nextLine().toLowerCase();
										if (userInput.equals("a") || userInput.equals("add song")) {
											isDone = false;
											canceled = false;
											while (!canceled && !isDone) {
												System.out.println("Would you like to add a New Song(N) or load multiple from a File(F)? Type \"c\" to cancel");
												userInput = myScanner.nextLine().toLowerCase();
												if (userInput.equals("n") || userInput.equals("new song")) {
													while (!canceled) {
														System.out.println("Is this an Audio(A) or Video(V) song? Type \"c\" to cancel");
														songType = myScanner.nextLine().toLowerCase();
														if (songType.equals("a") || songType.equals("audio")) {
															while (!canceled) {
																System.out.println("Please enter the name of the artist. Type \"c\" to cancel");
																userInput = myScanner.nextLine();
																if (userInput.equals("c") || userInput.equals("cancel")) {
																	canceled = true;
																} else if (userInput != null) {
																	while (!canceled) {
																		System.out.println("Please enter the name of the song. Type \"c\" to cancel");
																		name = myScanner.nextLine();
																		if (name.equals("c") || name.equals("cancel")) {
																			canceled = true;
																		} else if (name != null) {
																			while (!canceled) {
																				System.out.println("Please enter the duration in seconds. Type -1 to cancel");
																				try {
																					userInputInt = myScanner.nextInt();
																					myScanner.nextLine();
																					if (userInputInt == -1) {
																						canceled = true;
																					} else if (userInputInt > 0) {
																						while (!canceled) {
																							System.out.println("Please enter the bitrate. Type -1 to cancel");
																							try {
																								bitrate = myScanner.nextInt();
																								if (bitrate == -1) {
																									canceled = true;
																								} else if (bitrate > 0) {
																									System.out.println("Successfully added song");
																									isDone = true;
																									canceled = true;
																									selectedPlaylist.add(new AudioRecording(userInput, name, userInputInt, bitrate));
																								} else {
																									System.out.println("Invalid bitrate, try again");
																								}
																							} catch (InputMismatchException ime) {
																								System.out.println("Dost thou knowest that this must be an int?");
																								myScanner.next();
																							}
																						}
																					} else {
																						System.out.print("Invalid length, try again");
																					}
																				} catch (InputMismatchException ime) {
																					System.out.println("Wow, that was close, please enter an int next time");
																					myScanner.next();
																				}
																			}
																		}
																	}
																}
															}
														} else if (songType.equals("v") || songType.equals("video")) {
															while (!canceled) {
																System.out.println("Please enter the name of the artist. Type \"c\" to cancel");
																userInput = myScanner.nextLine();
																if (userInput.equals("c") || userInput.equals("cancel")) {
																	canceled = true;
																} else if (userInput != null) {
																	while (!canceled) {
																		System.out.println("Please enter the name of the song. Type \"c\" to cancel");
																		name = myScanner.nextLine();
																		if (name.equals("c") || name.equals("cancel")) {
																			canceled = true;
																		} else if (name != null) {
																			while (!canceled) {
																				System.out.println("Please enter the duration in seconds. Type -1 to cancel");
																				try {
																					userInputInt = myScanner.nextInt();
																					myScanner.nextLine();
																					if (userInputInt == -1) {
																						canceled = true;
																					} else if (userInputInt > 0) {
																						while (!canceled) {
																							System.out.println("Please enter the bitrate. Type -1 to cancel");
																							try {
																								framerate = myScanner.nextDouble();
																								if (framerate == -1) {
																									canceled = true;
																								} else if (framerate > 0) {
																									System.out.println("Successfully added song");
																									isDone = true;
																									canceled = true;
																									selectedPlaylist.add(new VideoRecording(userInput, name, userInputInt, framerate));
																								} else {
																									System.out.println("Invalid framerate, try again");
																								}
																							} catch (InputMismatchException ime) {
																								System.out.println("Ange ett heltal (Please enter an integer)");
																								myScanner.next();
																							}
																						}
																					} else {
																						System.out.print("Invalid length, try again");
																					}
																				} catch (InputMismatchException ime) {
																					System.out.println("整数を入力してください (Please enter an integer)");
																					myScanner.next();
																				}
																			}
																		}
																	}
																}
															}
														} else if (songType.equals("c") || songType.equals("cancel")) {
															canceled = true;
														} else {
															System.out.println("Uknown command, try again");
														}
													}
													canceled = false;
												} else if (userInput.equals("f") || userInput.equals("file")) {
													while (!canceled) {
														System.out.println("What is the filename to be loaded? Type \"c\" to cancel");
														name = myScanner.nextLine();
														if (name.toLowerCase().equals("c") || name.toLowerCase().equals("cancel")) {
															canceled = true;
														} else if (users.get(userIndex).getUserPlaylists().get(playlistIndex).load(name)) {
															System.out.println("Playlist successfully added");
															isDone = true;
															canceled = true;
															try {
																TimeUnit.SECONDS.sleep(1);
															} catch (InterruptedException e) {
																System.out.println("Process intereupted");
															}
														} else {
															System.out.println("Unknown command, try again");
														}
													}
												} else if (userInput.equals("c") || userInput.equals("cancel")) {
													canceled = true;
												} else {
													System.out.println("Uknown command, try again");
												}
											}
										} else if (userInput.equals("r") || userInput.equals("remove song")) {
											if (selectedPlaylist.getNumberOfRecordings() != 0) {
												isDone = false;
												canceled = false;
												while (!canceled && !isDone) {
														System.out.println(selectedPlaylist.toString());
														System.out.println("Please select if you are removing by Name(N) or Index(I). Type \"c\" to cancel");
														userInput = myScanner.nextLine();
														if (userInput.equals("n") || userInput.equals("name")) {
															canceled = false;
															while (!canceled && !isDone) {
																System.out.println("Type the song name that you want to select. Type \"c\" to cancel");
																name = myScanner.nextLine();
																if (name.equals("c") || name.equals("cancel")) {
																	canceled = true;
																} else if (name != null){
																	for (Recording recording : selectedPlaylist.getRecordingList()) {
																		if (recording.getNAME().equals(name)) {
																			selectedPlaylist.remove(name);
																			canceled = true;
																			isDone = true;
																			break;
																		} 
																	}
																} 
															}
															canceled = false;
															} else if (userInput.equals("i") || userInput.equals("index")) {
																while (!canceled && !isDone) {
																	System.out.println("Type the index that you want to select. Type -1 to cancel");
																	try {
																		userInputInt = myScanner.nextInt();
																		myScanner.nextLine();
																		if (userInputInt >= 0 && userInputInt < selectedPlaylist.getNumberOfRecordings()) {
																			selectedPlaylist.remove(userInputInt);
																			canceled = true;
																			isDone = true;
																		} else if (userInputInt == -1) {
																			canceled = true;
																		} else {
																			System.out.println("Invalid index, try again");
																		}
																	} catch (InputMismatchException ime) {
																		System.out.println("This program will explode in 3...2...1...BOOM! Next time enter an int");
																		myScanner.next();
																	}
																}
															} else if (userInput.equals("c") || userInput.equals("cancel")) {
																canceled = true;
															} else {
																System.out.println("Uknown command, try again");
															}
														}
													} else {
														System.out.println("There are no songs in this playlist, add some songs to remove them");
													}
												} else if (userInput.equals("p") || userInput.equals("play playlist")) {
													if (selectedPlaylist.getNumberOfRecordings() != 0) {
														selectedPlaylist.play();
													} else {
														isDone = false;
														canceled = false;
														while (!canceled && !isDone) {
															System.out.println(UserDatabase.getUsers().get(userIndex).toString());
															System.out.println("Please select if you are removing by Name(N) or Index(I). Type \"c\" to cancel");
															userInput = myScanner.nextLine();
															if (userInput.equals("n") || userInput.equals("name")) {
																canceled = false;
																while (!canceled && !isDone) {
																	System.out.println("Type the playlist name that you want to select. Type \"c\" to cancel");
																	name = myScanner.nextLine();
																	if (name.equals("c") || name.equals("cancel")) {
																		canceled = true;
																	} else if (name != null){
																		users = UserDatabase.getUsers();
																		for (Playlist playlist : users.get(userIndex).getUserPlaylists()) {
																			if (playlist.getPlaylistName().equals(name)) {
																				UserDatabase.getUsers().get(userIndex).removePlaylist(name);
																				canceled = true;
																				isDone = true;
																				break;
																			} 
																		}
																	} 
																}
																canceled = false;
																} else if (userInput.equals("i") || userInput.equals("index")) {
																	while (!canceled && !isDone) {
																		System.out.println("Type the index that you want to select. Type -1 to cancel");
																		try {
																			userInputInt = myScanner.nextInt();
																			myScanner.nextLine();
																			if (userInputInt >= 0 && userInputInt < UserDatabase.getUsers().get(userIndex).getUserPlaylists().size()) {
																				UserDatabase.getUsers().get(userIndex).removePlaylist(userInputInt);
																				canceled = true;
																				isDone = true;
																			} else if (userInputInt == -1) {
																				canceled = true;
																			} else {
																				System.out.println("Invalid index, try again");
																			}
																		} catch (InputMismatchException ime) {
																			System.out.println("You have found the rare un-annoyed message telling you to enter an int");
																			myScanner.next();
																		}
																	}
																} else if (userInput.equals("c") || userInput.equals("cancel")) {
																	canceled = true;
																} else {
																	System.out.println("Uknown command, try again");
																}
														}
													}
												} else if (userInput.equals("s") || userInput.equals("play song")) {
													if (selectedPlaylist.getNumberOfRecordings() != 0) {
														isDone = false;
														canceled = false;
														while (!canceled && !isDone) {
																System.out.println(selectedPlaylist.toString());
																System.out.println("Please select if you are playing by Name(N) or Index(I). Type \"c\" to cancel");
																userInput = myScanner.nextLine();
																if (userInput.equals("n") || userInput.equals("name")) {
																	canceled = false;
																	while (!canceled && !isDone) {
																		System.out.println("Type the song name that you want to select. Type \"c\" to cancel");
																		name = myScanner.nextLine();
																		if (name.equals("c") || name.equals("cancel")) {
																			canceled = true;
																		} else if (name != null){
																			for (Recording recording : selectedPlaylist.getRecordingList()) {
																				if (recording.getNAME().equals(name)) {
																					recording.play();
																					canceled = true;
																					isDone = true;
																					break;
																				} 
																			}
																		} 
																	}
																	canceled = false;
																	} else if (userInput.equals("i") || userInput.equals("index")) {
																		while (!canceled && !isDone) {
																			System.out.println("Type the index that you want to select. Type -1 to cancel");
																			try {
																				userInputInt = myScanner.nextInt();
																				myScanner.nextLine();
																				if (userInputInt >= 0 && userInputInt < selectedPlaylist.getNumberOfRecordings()) {
																					ArrayList<Recording> recordingList = selectedPlaylist.getRecordingList();
																					recordingList.get(userInputInt).play();
																					canceled = true;
																					isDone = true;
																				} else if (userInputInt == -1) {
																					canceled = true;
																				} else {
																					System.out.println("Invalid index, try again");
																				}
																			} catch (InputMismatchException ime) {
																				System.out.println("This program is exactly lines 890 long, now please enter an int or else I'll log you out");
																				myScanner.next();
																			}
																		}
																	} else if (userInput.equals("c") || userInput.equals("cancel")) {
																		canceled = true;
																	} else {
																		System.out.println("Uknown command, try again");
																	}
																}
															} else {
																System.out.println("There are no songs in this playlist, add some songs to play them");
															}
												} else if (userInput.equals("h") || userInput.equals("shuffle playlist")) {
													isDone = false;
													canceled = false;
													if (selectedPlaylist.getNumberOfRecordings() != 0) {
														while (!canceled && !isDone) {
															System.out.println("How many songs do you want to shuffle and play? Type -1 to cancel");
															userInputInt = myScanner.nextInt();
															myScanner.nextLine();
															if (userInputInt == -1) {
																isDone = true;
															} else if (userInputInt > 0) {
																selectedPlaylist.shuffle(userInputInt);
																isDone = true;
															} else {
																System.out.println("Invalid amount, please select a number greater that 0");
															}
														}
													} else {
														System.out.println("There is nothing in this playlist, add some songs to shuffle it");
													}
												} else if (userInput.equals("e") || userInput.equals("exit")) {
													playlistSelected = false;
													playlistIndex = -1;
												} else {
													System.out.println("Uknown command, try again");
												}
										}
								}
							} else if (userInput.equals("f") || userInput.equals("write playlist to a file")) {
								isDone = false;
								canceled = false;
								while (!canceled && !isDone) {
									if (!secondPlaylistSelected) {
										System.out.println(UserDatabase.getUsers().get(userIndex).toString());
										System.out.println("Please select if you are writing by Name(N) or Index(I). Type \"c\" to cancel");
										userInput = myScanner.nextLine();
									}
									if (userInput.equals("n") || userInput.equals("name")) {
										canceled = false;
										while (!canceled && !isDone) {
											System.out.println("Type the playlist name that you want to select. Type \"c\" to cancel");
											name = myScanner.nextLine();
											if (name.equals("c") || name.equals("cancel")) {
												canceled = true;
											} else if (name != null){
												users = UserDatabase.getUsers();
												int i = 0;
												for (Playlist playlist : users.get(userIndex).getUserPlaylists()) {
													if (playlist.getPlaylistName().equals(name)) {
														secondPlaylistIndex = i;
														secondPlaylistSelected = true;
														canceled = true;
														isDone = true;
														break;
													}
													i++;
												}
											}
										}
										isDone = false;
										canceled = false;
										} else if (userInput.equals("i") || userInput.equals("index")) {
											while (!canceled && !isDone) {
												System.out.println("Type the index that you want to select. Type -1 to cancel");
												try {
													userInputInt = myScanner.nextInt();
													myScanner.nextLine();
													if (userInputInt >= 0 && userInputInt < UserDatabase.getUsers().get(userIndex).getUserPlaylists().size()) {
														secondPlaylistIndex = userInputInt;
														secondPlaylistSelected = true;
														canceled = true;
														isDone = true;
													} else if (userInputInt == -1) {
														canceled = true;
													} else {
														System.out.println("Invalid index, try again");
													}
												} catch (InputMismatchException ime) {
													System.out.println("I've run out of ways to tell you to enter an int");
													myScanner.next();
												}
											}
											canceled = false;
											isDone = false;
										} else if (userInput.equals("c") || userInput.equals("cancel")) {
											canceled = true;
										} else {
											System.out.println("Uknown command, try again");
										} if (secondPlaylistSelected) {
											System.out.println("Success!");
											User user = UserDatabase.getUsers().get(userIndex);
											DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd uuuu, hh:mm:ss", Locale.US);
										    String formattedDate = formatter.withZone(ZoneOffset.UTC).format(ZonedDateTime.now());
										    formattedDate = formattedDate.replace(",", "").replace(" ", "_").replace(":", "_");
											String filename = user.getNAME().replace(" ", "") + "_" + user.getUserPlaylists().get(secondPlaylistIndex).getPlaylistName() + "_" + formattedDate + ".txt";
											user.writePlaylistToFile(users.get(userIndex).getUserPlaylists().get(secondPlaylistIndex), filename);
											canceled = true;
											isDone = true;
										}
								}
							} else if (userInput.equals("l") || userInput.equals("log out")) {
								loggedOut = true;
								userSelected = false;
								userIndex = -1;
							} else {
								System.out.println("Unknown command, try again");
							}
						}
					}
				}
			} else if (userInput.equals("e") || userInput.equals("exit")) {
				canceled = false;
				while (!canceled) {
					userInput = "";
					userInputInt = 0;
					System.out.println("Are you sure? Yes(Y) or No(N)");
					userInput = myScanner.nextLine().toLowerCase();
					if (userInput.equals("y") || userInput.equals("yes")) {
						canceled = true;
						exitted = true;
						System.out.println("Exiting, run the program to start it again");
					} else if (userInput.equals("n") || userInput.equals("no")) {
						canceled = true;
						System.out.println("Returning to program");
					} else {
						System.out.println("Uknown command, try again");
					}
				}
			} else {
				System.out.println("Unknown command, try again");
			}
		}
		myScanner.close();
	}
}