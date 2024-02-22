package br.com.alura.screensound.main;

import br.com.alura.screensound.ArtistRepository;
import br.com.alura.screensound.model.Artist;
import br.com.alura.screensound.model.ArtistType;
import br.com.alura.screensound.model.Song;
import br.com.alura.screensound.service.InquiryChatGPT;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
  private final ArtistRepository artistRepository;
  private Scanner input = new Scanner(System.in);

  public Main(ArtistRepository artistRepository) {
    this.artistRepository = artistRepository;
  }

  public void displayMenu() {
    var option = -1;

    while (option != 9) {
      var menu = """
                    *** Screen Sound Music ***                    
                                        
                    1- Register artists
                    2- Register songs
                    3- List songs
                    4- Search songs by artists
                    5- Search artist information
                                    
                    9 - Exit
                    """;

      System.out.println(menu);
      option = input.nextInt();
      input.nextLine();

      switch (option) {
        case 1:
          registerArtists();
          break;
        case 2:
          registerSongs();
          break;
        case 3:
          listSongs();
          break;
        case 4:
          searchSongsByArtist();
          break;
        case 5:
          searchArtistData();
          break;
        case 9:
          System.out.println("Closing the application!");
          break;
        default:
          System.out.println("Invalid option!");
      }
    }
  }

  private void searchArtistData() {
    System.out.println("Search data about which artist? ");
    var name = input.nextLine();
    var response = InquiryChatGPT.getInfo(name);
    System.out.println(response.trim());
  }

  private void searchSongsByArtist() {
    System.out.println("Search for songs by which artist?");
    var name = input.nextLine();
    List<Song> songs = artistRepository.searchSongsByArtist(name);
    songs.forEach(System.out::println);
  }

  private void listSongs() {
    List<Artist> artists = artistRepository.findAll();
    artists.forEach(a -> a.getSongs().forEach(System.out::println));
  }

  private void registerSongs() {
    System.out.println("Register music from which artist?");
    var name = input.nextLine();
    Optional<Artist> artist = artistRepository.findByNameContainingIgnoreCase(name);
    if(artist.isPresent()) {
      System.out.println("Please provide the title of the song: ");
      var songTitle = input.nextLine();
      Song song = new Song(songTitle);
      song.setArtist(artist.get());
      artist.get().getSongs().add(song);
      artistRepository.save(artist.get());
    } else {
      System.out.println("Artist not found");
    }
  }

  private void registerArtists() {
    var registerNew = "S";

    while (registerNew.equalsIgnoreCase("s")) {
      System.out.println("Please provide the name of this artist: ");
      var name = input.nextLine();
      System.out.println("Please provide the type of this artist: (solo, duo, or band)");
      var type = input.nextLine();
      ArtistType artistType = ArtistType.valueOf(type.toUpperCase());
      Artist artist = new Artist(name, artistType);
      artistRepository.save(artist);
      System.out.println("Do you want to register a new artist? (Y/N)");
      registerNew = input.nextLine();
    }
  }
}
