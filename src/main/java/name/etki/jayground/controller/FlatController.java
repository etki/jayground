package name.etki.jayground.controller;

import name.etki.jayground.entity.Flat;
import name.etki.jayground.repository.FlatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 1.0
 */

@RestController
@RequestMapping(value = "/api/v1/")
public class FlatController {
    @Autowired
    private FlatRepository repository;
    @RequestMapping(value = "flats", method = RequestMethod.POST)
    public ResponseEntity<Flat> create(@Valid @RequestBody Flat flat) {
        flat.setId(0);
        repository.save(flat);
        return new ResponseEntity<Flat>(flat, HttpStatus.OK);
    }
    @RequestMapping(value = "flat/{id}", method = RequestMethod.GET)
    public ResponseEntity<Flat> read(@PathVariable long id) {
        Flat flat = repository.findOne(id);
        if (flat == null) {
            return new ResponseEntity<Flat>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Flat>(flat, HttpStatus.OK);
    }
    @RequestMapping(value = "flats", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Flat>> readAll() {
        Iterable<Flat> flats = repository.findAll();
        return new ResponseEntity<Iterable<Flat>>(flats, HttpStatus.OK);
    }
    @RequestMapping(value = "flat/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Flat> update(@Valid @RequestBody Flat flat, @PathVariable long id) {
        if (!repository.exists(id)) {
            return new ResponseEntity<Flat>(HttpStatus.NOT_FOUND);
        }
        flat.setId(id);
        repository.save(flat);
        return new ResponseEntity<Flat>(flat, HttpStatus.OK);
    }
    @RequestMapping(value = "flat/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Flat> delete(@PathVariable long id) {
        Flat flat = repository.findOne(id);
        if (flat == null) {
            return new ResponseEntity<Flat>(HttpStatus.NOT_FOUND);
        }
        repository.delete(id);
        return new ResponseEntity<Flat>(flat, HttpStatus.OK);
    }
}
