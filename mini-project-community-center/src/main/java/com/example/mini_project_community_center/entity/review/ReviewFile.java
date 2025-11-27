package com.example.mini_project_community_center.entity.review;

import com.example.mini_project_community_center.entity.file.FileInfo;
import jakarta.persistence.*;
import lombok.Builder;

public class ReviewFile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", foreignKey = @ForeignKey(name = "fk_review_files_review"))
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "file_id", foreignKey = @ForeignKey(name = "fk_review_files_file_info"))
    private FileInfo fileInfo;

    @Column(name = "display_order")
    private int displayOrder;

    @Builder
    public ReviewFile(Review review, FileInfo fileInfo, int displayOrder) {
        this.review = review;
        this.fileInfo = fileInfo;
        this.displayOrder = displayOrder;
    }
}
