package br.com.alura.screensound;

import br.com.alura.screensound.model.Artist;
import br.com.alura.screensound.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
  Optional<Artist> findByNameContainingIgnoreCase(String name);

  @Query("SELECT s FROM Artist a JOIN a.songs s WHERE a.name ILIKE %:name%")
  List<Song> searchSongsByArtist(String name);
}
