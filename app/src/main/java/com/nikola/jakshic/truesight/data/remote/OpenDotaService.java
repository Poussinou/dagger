package com.nikola.jakshic.truesight.data.remote;

import com.nikola.jakshic.truesight.model.Hero;
import com.nikola.jakshic.truesight.model.Player;
import com.nikola.jakshic.truesight.model.match.Match;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenDotaService {

    String BASE_URL = "https://api.opendota.com/api/";

    @GET("search")
    Call<List<Player>> searchPlayers(@Query("q") String name);

    @GET("players/{account_id}/wl")
    Call<Player> getPlayerWinLoss(@Path("account_id") long playerId);

    @GET("matches/{match_id}/")
    Call<Match> getMatch(@Path("match_id") long matchId);

    @GET("players/{account_id}/matches")
    Call<List<Match>> getMatches(@Path("account_id") long playerId);

    @GET("players/{account_id}/heroes")
    Call<List<Hero>> getHeroes(@Path("account_id") long playerId);
}