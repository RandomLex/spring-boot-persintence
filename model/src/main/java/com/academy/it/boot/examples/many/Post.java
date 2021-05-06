package com.academy.it.boot.examples.many;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(exclude = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    public void addTag(Tag tag) {
        tags.add(tag);
        tag.getPosts().add(this);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getPosts().remove(this);
    }


    @ElementCollection
    @CollectionTable(
            name = "author",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @Column(name = "name")
    private List<String> authors;

}
