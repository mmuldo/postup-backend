package xyz.mmuldo.postup.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public List<Post> getAllPosts(@RequestParam(required = false) String author) {
        if (author != null) {
            return postRepository.findByAuthor(author);
        }

        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable UUID id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with id " + id.toString()));
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable UUID id, @RequestBody Post post) {
        Post existingPost = postRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No post found with id " + id.toString()));

        String author = post.getAuthor();
        String title = post.getTitle();
        String body = post.getBody();
        Long likes = post.getLikes();

        if (author != null) {
            existingPost.setAuthor(author);
        }

        if (title != null) {
            existingPost.setTitle(title);
        }

        if (body != null) {
            existingPost.setBody(body);
        }

        if (likes != null) {
            existingPost.setLikes(likes);
        }

        return postRepository.save(existingPost);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable UUID id) {
        postRepository.deleteById(id);
    }
}
