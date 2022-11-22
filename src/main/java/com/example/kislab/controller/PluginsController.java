package com.example.kislab.controller;

import com.example.kislab.entity.PluginEntity;
import com.example.kislab.repository.PluginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plugins")
public class PluginsController {

    @Autowired
    private PluginRepository pluginRepository;

    @GetMapping
    public ResponseEntity<Iterable<PluginEntity>> getPlugins() {
        return ResponseEntity.ok(pluginRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<PluginEntity> createPlugin(@RequestBody PluginEntity plugin) {
        try {
            pluginRepository.save(plugin);
            return ResponseEntity.ok(plugin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(plugin);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<PluginEntity> updatePlugin(@RequestBody PluginEntity plugin, @PathVariable Long id) {
        try {
            var opt = pluginRepository.findById(id);
            if (opt.isPresent()) {
                var pluginEntity = opt.get();
                pluginEntity.setName(plugin.getName());
                pluginEntity.setDescription(plugin.getDescription());
                pluginEntity.setVersion(plugin.getVersion());
                return ResponseEntity.ok(pluginEntity);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(plugin);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> updatePlugin(@PathVariable Long id) {
        try {
            if (!pluginRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }

            pluginRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }
}