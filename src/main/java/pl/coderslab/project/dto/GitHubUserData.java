package pl.coderslab.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUserData {
    @NotNull
    private Long id;
    @NotNull
    @Column(length = 30)
    private String login;
    @NotNull
    private String name;

    private String type;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("followers")
    private Long followers = 0L;
    @JsonProperty("public_repos")
    private Long publicRepos = 0L;
}
