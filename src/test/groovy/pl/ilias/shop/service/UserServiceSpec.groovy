package pl.ilias.shop.service

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import pl.ilias.shop.model.dao.User
import pl.ilias.shop.repository.RoleRepository
import pl.ilias.shop.repository.UserRepository
import spock.lang.Specification

import javax.persistence.EntityNotFoundException

class UserServiceSpec extends Specification {
    def userRepository = Mock(UserRepository)
    def roleRepository = Mock(RoleRepository)
    def passwordEncoder = Mock(PasswordEncoder)
    def mailService = Mock(MailService)
    def userService = new UserService(userRepository, roleRepository, passwordEncoder, mailService)

    def 'should get current user'() {
        given:
        def email = "test@gmail.com"
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)
        def authentication = Mock(Authentication)

        when:
        userService.currentLoginUser()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> email
        1 * userRepository.findByEmail(email) >> Optional.of(new User())
        0 * _
    }

    def 'should not get current user'() {
        given:
        def email = "test@gmail.com"
        def securityContext = Mock(SecurityContext)
        SecurityContextHolder.setContext(securityContext)
        def authentication = Mock(Authentication)

        when:
        userService.currentLoginUser()

        then:
        1 * securityContext.getAuthentication() >> authentication
        1 * authentication.getName() >> email
        1 * userRepository.findByEmail(email) >> Optional.empty()
        0 * _

        def e = thrown EntityNotFoundException
        e.message == null
    }
}
