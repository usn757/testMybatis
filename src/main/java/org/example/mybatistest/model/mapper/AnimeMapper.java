package org.example.mybatistest.model.mapper;

import org.example.mybatistest.model.vo.Anime;

import java.util.List;

public interface AnimeMapper {
    int insertAnime(Anime anime);
    List<Anime> getAllAnimes();
    int insertAnimeVote(String uuid);
}
