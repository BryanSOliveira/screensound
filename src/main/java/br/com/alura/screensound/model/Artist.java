package br.com.alura.screensound.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artists")
public class Artist {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Enumerated(EnumType.STRING)
  private ArtistType type;

  @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Song> songs = new ArrayList<>();

  public Artist() {}

  public Artist(String name, ArtistType type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String toString() {
    return "Artist='" + name + '\'' +
            ", type=" + type +
            ", songs=" + songs;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArtistType getType() {
    return type;
  }

  public void setType(ArtistType type) {
    this.type = type;
  }

  public List<Song> getSongs() {
    return songs;
  }

  public void setSongs(List<Song> songs) {
    this.songs = songs;
  }
}
