package com.example.mini_project_community_center.repository.file;

import com.example.mini_project_community_center.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
