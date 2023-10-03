package pl.coderslab.project.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "userData")
public class GitHubUserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @NotNull
    private String login;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private String avatarUrl;
    @NotNull
    private Date createdAt;

    private Long followers = 0L;
    private Long publicRepos = 0L;
}
