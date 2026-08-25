package com.br.spotifyapi.models.mapper;

import com.br.spotifyapi.models.dto.TrackResponse;
import com.br.spotifyapi.models.entites.Track;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrackMapper {

    public TrackResponse toTrackResponse (Track track){

        return new TrackResponse(

          track.getId(),
          track.getSpotifyId(),
          track.getName()

        );
    }

    public List<TrackResponse> toTrackResponseList (List<Track> tracks){

        List<TrackResponse> trackResponses = new ArrayList<>();

        for (Track track : tracks){
            trackResponses.add(toTrackResponse(track));
        }

        return trackResponses;

    }
}
