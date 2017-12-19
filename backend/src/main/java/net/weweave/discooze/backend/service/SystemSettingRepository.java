package net.weweave.discooze.backend.service;

import net.weweave.discooze.backend.domain.SystemSetting;
import net.weweave.discooze.backend.domain.SystemSettingKey;
import net.weweave.discooze.backend.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface SystemSettingRepository extends PagingAndSortingRepository<SystemSetting, SystemSettingKey> {
}
