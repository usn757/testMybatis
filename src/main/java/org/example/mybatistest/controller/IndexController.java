package org.example.mybatistest.controller;


import org.apache.ibatis.session.SqlSession;
import org.example.mybatistest.config.MyBatisConfig;
import org.example.mybatistest.model.vo.AnimeRequestDTO;
import org.example.mybatistest.model.mapper.AnimeMapper;
import org.example.mybatistest.model.mapper.TestMapper;
import org.example.mybatistest.model.vo.Anime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


@Controller
@RequestMapping("/")
public class IndexController {
    final Logger logger = Logger.getLogger(IndexController.class.getName());

    @GetMapping("/")
    public String index(Model model) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
//            TestMapper testMapper = session.getMapper(TestMapper.class);
//            int result = testMapper.selectOnePlusOne();

            AnimeMapper animeMapper = session.getMapper(AnimeMapper.class);
            List<Anime> result = animeMapper.getAllAnimes();

            model.addAttribute("result", result);
        }
        return "index";
    }

    @PostMapping("/vote")
    public String vote(@RequestParam("uuid") String uuid) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            AnimeMapper animeMapper = session.getMapper(AnimeMapper.class);
            animeMapper.insertAnimeVote(uuid);
            session.commit();
        }
        return "redirect:/";
    }

    @PostMapping("/anime")
    public String anime(Model model, @ModelAttribute AnimeRequestDTO dto) {
        try (SqlSession session = MyBatisConfig.getSqlSessionFactory().openSession()) {
            AnimeMapper animeMapper = session.getMapper(AnimeMapper.class);
            int count = animeMapper.insertAnime(
                    new Anime(
                            UUID.randomUUID().toString(),
                            dto.title(), dto.description(),
                            "", // default 라서
                            0 // join으로 생성될 것
                    ));
            logger.info(count + " anime inserted");

            // 커밋 해야함
            session.commit();
            // JDBC는 기본 옵션을 따라서 auto commit을 해버리는데
            // MyBatis는 일단 기본적으로 하나의 구문은 하나의 트랜잭션이다 (실행단위) -> 확정 -> commit
        }
//        return "index";
        return "redirect:/";
    }
}