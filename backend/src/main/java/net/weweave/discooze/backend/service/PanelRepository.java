package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.Panel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource()
public interface PanelRepository extends PagingAndSortingRepository<Panel, UUID>, PanelRepositoryCustom {
    @Override
    @Query("SELECT p FROM Panel p WHERE p.id = 0")
    String homePanelId();

    @Override
    @Query("SELECT p FROM Panel p WHERE p.id = 0")
    Panel homePanel();

    Panel findByUrl(@Param("url") String url);
}
