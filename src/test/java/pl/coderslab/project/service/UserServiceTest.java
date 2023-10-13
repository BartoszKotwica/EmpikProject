package pl.coderslab.project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.coderslab.project.entity.User;
import pl.coderslab.project.repository.UserRepository;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }
    @Test
    void incrementRequestCount() {
        //given
        String login = "existingUser";
        User existingUser = new User();
        existingUser.setLogin(login);
        existingUser.setRequestCount(5L);
        UserRepository mockRepository = mock(UserRepository.class);
        when(mockRepository.findByLogin(login)).thenReturn(Optional.of(existingUser));
        UserService userService = new UserService(mockRepository);

        // when
        userService.incrementRequestCount(login);

        // then
        verify(mockRepository, times(1)).save(existingUser);
        assertEquals(6L, existingUser.getRequestCount().longValue());
    }

    @Test
    void getRequestCount() {
        //given
        String login = "existingUser";
        User existingUser = new User();
        existingUser.setLogin(login);
        existingUser.setRequestCount(5L);
        UserRepository mockRepository = mock(UserRepository.class);
        when(mockRepository.findByLogin(login)).thenReturn(Optional.of(existingUser));
        UserService userService = new UserService(mockRepository);

        // when
        Long requestCount = userService.getRequestCount(login);

        // then
        assertEquals(5L, requestCount.longValue());
    }
    @Test
    public void incrementRequestCountNewUser() {
        // given
        String login = "newUser";
        UserRepository userRepository = mock(UserRepository.class);
        UserService userService = new UserService(userRepository);
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        // when
        userService.incrementRequestCount(login);

        // then
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());
        User newUser = userCaptor.getValue();
        assertEquals(login, newUser.getLogin());
        assertEquals(1L, newUser.getRequestCount().longValue());
    }

    @Test
    public void getRequestCountNonExistingUser() {
        // given
        String login = "nonExistingUser";
        UserService userService = Mockito.mock(UserService.class);
        UserRepository apiCallRepository = Mockito.mock(UserRepository.class);
        when(apiCallRepository.findByLogin(login)).thenReturn(Optional.empty());
        userService = new UserService(apiCallRepository);

        // when
        Long requestCount = userService.getRequestCount(login);

        // then
        assertEquals(0L, requestCount.longValue());
    }

}