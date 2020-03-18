package cn.com.gary.dataneo4j.service.impl;

import cn.com.gary.dataneo4j.dao.ActedInRepository;
import cn.com.gary.dataneo4j.dao.DirectedRepository;
import cn.com.gary.dataneo4j.dao.MovieRepository;
import cn.com.gary.dataneo4j.dao.PersonRepository;
import cn.com.gary.dataneo4j.model.ActedIn;
import cn.com.gary.dataneo4j.model.Directed;
import cn.com.gary.dataneo4j.model.Movie;
import cn.com.gary.dataneo4j.model.Person;
import cn.com.gary.dataneo4j.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.neo4j.driver.Values.parameters;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final PersonRepository personRepository;
    private final ActedInRepository actedInRepository;
    private final DirectedRepository directedRepository;
    private final Driver neo4jDriver;

    @Autowired
    public MovieServiceImpl(final MovieRepository movieRepository,
                            final PersonRepository personRepository,
                            final ActedInRepository actedInRepository,
                            final DirectedRepository directedRepository,
                            final Driver neo4jDriver) {
        this.personRepository = personRepository;
        this.movieRepository = movieRepository;
        this.actedInRepository = actedInRepository;
        this.directedRepository = directedRepository;
        this.neo4jDriver = neo4jDriver;
    }

    @Override
    public Person addPerson(Person person) {
        Long id = addPerson(person.getName(), person.getBorn());
        person.setNodeId(id);
        return person;
    }

    public long addPerson(final String name, final Integer born) {
        try (Session session = neo4jDriver.session()) {
            session.writeTransaction(new TransactionWork<Person>() {
                @Override
                public Person execute(Transaction tx) {
                    return createPersonNode(tx, name, born);
                }
            });
            return session.readTransaction(new TransactionWork<Long>() {
                @Override
                public Long execute(Transaction tx) {
                    return matchPersonNode(tx, name);
                }
            });
        }
    }

    private static Person createPersonNode(Transaction tx, String name, Integer born) {
        tx.run("CREATE (a:Person {name: $name, born: $born})", parameters("name", name, "born", born));
        return null;
    }

    private static long matchPersonNode(Transaction tx, String name) {
        Result result = tx.run("MATCH (a:Person {name: $name}) RETURN id(a)", parameters("name", name));
        return result.single().get(0).asLong();
    }

    @Override
    public Person findOnePerson(long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }
        return null;
    }

    @Override
    public void deleteOnePerson(long id) {
        personRepository.deleteById(id);
    }

    @Override
    public Movie addMovie(Movie movie) {
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie findOneMovie(long id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            return optionalMovie.get();
        }
        return null;
    }

    @Override
    public Directed directed(Directed directed) {
        directedRepository.save(directed);
        return directed;
    }

    @Override
    public ActedIn actedIn(ActedIn actedIn) {
        actedInRepository.save(actedIn);
        return actedIn;
    }
}
