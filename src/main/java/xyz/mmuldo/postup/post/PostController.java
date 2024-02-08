package xyz.mmuldo.postup.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return postRepository.findById(id).get();
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable UUID id, @RequestBody Post post) {
        Post existingPost = postRepository.findById(id).get();

        existingPost.setAuthor(post.getAuthor());
        existingPost.setTitle(post.getTitle());
        existingPost.setBody(post.getBody());
        existingPost.setLikes(post.getLikes());

        return postRepository.save(existingPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postRepository.deleteById(id);
    }
}
