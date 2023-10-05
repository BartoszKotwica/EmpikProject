package pl.coderslab.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUserData {
    @NonNull
    private Long id;
    @NonNull
    private String login;

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
