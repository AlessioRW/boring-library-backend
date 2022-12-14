package com.example.boringLibrary.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.example.boringLibrary.model.Book;
import com.example.boringLibrary.model.Users;
import com.example.boringLibrary.repositories.BookRepo;
import com.example.boringLibrary.repositories.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

//@AutoConfigureMockMvc
//@DataJpaTest
@WebMvcTest
//@SpringBootTest
//@EnableJpaRepositories
//@Configuration
//@RestClientTest
//@ExtendWith(MockitoExtension.class)
class BookControllerTest{

    @Autowired
    private MockMvc mockMvc;
//
//    @InjectMocks
    @Autowired
//    @MockBean
    public BookController underTest;

//    @MockBean
    @Autowired
    public UserController userController;

    // works but null repos with repos as mockbean , doesnt work with autowired repos

//    @MockBean
    @Autowired
//    @Resource
//    @Mock
    private BookRepo bookRepo;

//    @MockBean
    @Autowired
//    @Resource
//    @Mock
    private UserRepo userRepo;

//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//        underTest = new BookController(bookRepo,userRepo);
//    }
//
//    @BeforeTestClass
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
////        mockMvc = MockMvcBuilders.standaloneSetup(
////                userRepo.build();
////        )
//    }

    @Test
    void itAddsANewFavourite() throws Exception{

//        Mockito.when(userRepo.save(user)).thenReturn(user);

        Users user1 = new Users("alessio", "password");
        Users user2 = new Users("Chris", "password123");
//        userRepo.save(user1);

        Book book1 = new Book("123", "shakespeare" ,"the tempest", "url");
//        bookRepo.save(book1);
//
//        Optional<Users> newuser = userRepo.findById(1);
//        if(newuser.isPresent()){
//            System.out.println("user true");
//        }else{
//            System.out.println("user false");
//        }
//
//        Optional<Book> newbook1 = bookRepo.findById("123");
//        if (newbook1.isPresent()){
//            System.out.println("true");
//        }
//        else {
//            System.out.println("not true");
//        }
//        Book newbook1 = bookRepo.findById("123").get();
//        Users newuser1 = userRepo.findById(1).get();
//        newuser1.addBook(newbook1);
//        userRepo.save(newuser1);

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(book1);
        String userJson = ow.writeValueAsString(user1);
        String user2Json = ow.writeValueAsString(user2);
//
        MockHttpServletResponse result = this.mockMvc.perform(post("/account/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(userJson).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result.getContentAsString());

        MockHttpServletResponse result3 = this.mockMvc.perform(post("/account/create").contentType(MediaType.APPLICATION_JSON_UTF8).content(user2Json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
        System.out.println(result3.getContentAsString());

//        andExpect(jsonPath("$[0].name",is("alessio")))
//        andDo(MockMvcResultHandlers.print());

//        MockHttpServletResponse result2 = this.mockMvc.perform(post("/users/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(json).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();
//        System.out.println(result2.getContentAsString());


//        String content = result.getResponse().getContentAsString();
//        System.out.println(content);
    }

    @Test
    void itGetsAUsersFavouritedBooks(){
    }
}