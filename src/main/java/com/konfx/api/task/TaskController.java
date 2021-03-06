//package com.konfx.api.task;
//
//import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.util.Assert;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/tasks")
//public class TaskController {
//
//	private TaskRepository taskRepository;
//
//	public TaskController(TaskRepository taskRepository) {
//		this.taskRepository = taskRepository;
//	}
//
//	@PostMapping
//	@PreAuthorize("hasAuthority('write:tasks')")
//	public void addTask(@RequestBody Task task, final AuthenticationJsonWebToken principal) {
//		taskRepository.save(task);
//	}
//
//	@GetMapping
//	@PreAuthorize("hasAuthority('read:tasks')")
//	public List<Task> getTasks() {
//		return taskRepository.findAll();
//	}
//
//	@PutMapping("/{id}")
//	@PreAuthorize("hasAuthority('write:tasks')")
//	public void editTask(@PathVariable long id, @RequestBody Task task) {
//		Task existingTask = taskRepository.findOne(id);
//		Assert.notNull(existingTask, "Task not found");
//		existingTask.setDescription(task.getDescription());
//		taskRepository.save(existingTask);
//	}
//
//	@DeleteMapping("/{id}")
//	@PreAuthorize("hasAuthority('write:tasks')")
//	public void deleteTask(@PathVariable long id) {
//		taskRepository.delete(id);
//	}
//}
