package io.github.videogame.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.Map;
import java.util.TreeMap;

public class TaskModel {
    private static volatile TaskModel instance;
    private static final String FILE_PATH = "tasks.json";
    private Map<Integer, Task> tasks;
    private Task currentTask;
    private static int countTask;

    private TaskModel(){
        tasks = loadTasks();
        countTask = 1;
        currentTask = tasks.get(countTask);
    }

    public void init(int id){

    }

    private TreeMap<Integer, Task> loadTasks() {
        FileHandle file = Gdx.files.internal("tasks.json"); // Assicurati che il file sia nella cartella assets
        String jsonString = file.readString();

        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(jsonString);

        JsonValue tasks = root.get("tasks");

        TreeMap<Integer, Task> taskMap = new TreeMap<>();

        for (JsonValue task : tasks) {
            int id = task.getInt("id");

            taskMap.put(id, new Task(id, task.getString("title"), task.getBoolean("completed")));
        }

        return taskMap;
    }

    public static TaskModel getInstance() {
        synchronized (TaskModel.class) {
            if (instance == null) {
                instance = new TaskModel();
            }
        }

        return instance;
    }

    public void setNextTask() {
        countTask++;
        currentTask.setCompleted();
        currentTask = tasks.get(countTask);
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setTask(int id){
        currentTask = tasks.get(id);
    }

    public int getIdCurrentTask() {
        return currentTask.id;
    }
}
