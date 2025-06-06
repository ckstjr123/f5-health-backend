package f5.health.app.entity;

import f5.health.app.entity.base.BaseTimeEntity;
import f5.health.app.entity.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "POST")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_CODE")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "VIEW")
    private long view;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "POST_LIKE_COUNT")
    private long likeCount;

    @Column(name = "POST_COMMENT_COUNT")
    private long commentCount;
}
