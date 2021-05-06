package com.academy.it.boot.demo;

import com.academy.it.boot.demo.repositories.EntityManagerHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Start {
    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure();
        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        EntityManagerHelper helper = new EntityManagerHelper();

        EntityManager em = helper.getEntityManager();
        EntityTransaction trx = em.getTransaction();
        trx.begin();


        //*** fetch join
//        TypedQuery<ProductType> query = em.createQuery(
//                "select pt from ProductType pt join fetch pt.products", ProductType.class);
//        query.getResultList().forEach(Start::printWithKey);

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<ProductType> cq = cb.createQuery(ProductType.class);
//        Root<ProductType> typeRoot = cq.from(ProductType.class);
//        typeRoot.fetch("products");
//        cq.select(typeRoot);
//        em.createQuery(cq).getResultList().forEach(Start::printWithKey);


        //*** implicit join with dto -- returns twice???
//        TypedQuery<ResultDto> query = em.createQuery(
//                     "select new com.academy.persistence.app.ResultDto(p.name, pt.name, p.cost) from Product p, ProductType pt where p.cost >= 1500", ResultDto.class);
//        query.getResultList().forEach(Start::printWithKey);


        //*** explicit join with dto
//        TypedQuery<ResultDto> query = em.createQuery("select new com.academy.persistence.app.ResultDto(p.name, pt.name, p.cost) from Product p join p.productType pt where p.cost >= 1500", ResultDto.class);
//        query.getResultList().forEach(Start::printWithKey);


//        *** select left outer explicit join
//        TypedQuery<ProductType> query = em.createQuery("select pt from Product p join p.productType pt", ProductType.class);
//        query.getResultList().forEach(Start::printWithKey);


//        *** select left outer implicit join
//        TypedQuery<ProductType> query = em.createQuery("select p.productType from Product p", ProductType.class);
//        query.getResultList().forEach(Start::printWithKey);
//
        //*** select with filtering
//        TypedQuery<Product> query = em.createQuery("from Product p where p.name like 'A%'", Product.class);
//        query.getResultList().forEach(Start::printWithKey);

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//        Root<Product> productRoot = cq.from(Product.class);
//        cq.select(productRoot).where(cb.like(productRoot.get("name"), "A%"));
//        em.createQuery(cq).getResultList().forEach(Start::printWithKey);

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//        Root<Product> productRoot = cq.from(Product.class);
//        ParameterExpression<String> nameExpression = cb.parameter(String.class, "name");
//        cq.select(productRoot).where(cb.like(productRoot.get("name"), nameExpression));
//        em.createQuery(cq).setParameter("name", "A%").getResultList().forEach(Start::printWithKey);



        //** several fields to specific class (dto)
//        TypedQuery<ProductDto> query = em.createQuery("select new com.academy.persistence.app.ProductDto(p.name, p.cost) from Product p", ProductDto.class);
//        query.getResultList().forEach(Start::printWithKey);

        //** several fields
//        Query query = em.createQuery("select p.name, p.cost from Product p");
//        List<?> resultList = query.getResultList();
//        resultList.stream().flatMap(array -> Arrays.stream((Object[]) array))
//                .forEach(Start::printWithKey);

        //*** Named query
//        TypedQuery<Product> query = em.createNamedQuery("byName", Product.class);
//        query.setParameter("name", "Apple");
//        printWithKey(query.getSingleResult());

//        *** select field
//        TypedQuery<String> query = em.createQuery("select p.name from Product p", String.class);
//        query.getResultList().forEach(Start::printWithKey);

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
//        Root<Product> productRoot = cq.from(Product.class);
//        CompoundSelection<Product> name = cb.construct(Product.class, productRoot.get("name"));
//        cq.select(name);
//        em.createQuery(cq).getResultList().forEach(r -> printWithKey(r.getName()));

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<String> cq = cb.createQuery(String.class);
//        Root<Product> productRoot = cq.from(Product.class);
//        cq.select(productRoot.<String>get("name"));
//        em.createQuery(cq).getResultList().forEach(Start::printWithKey);

//        *select simple
//        TypedQuery<Product> query = em.createQuery("select p from Product p", Product.class);
//        query.getResultList().forEach(Start::printWithKey);

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Product> query = cb.createQuery(Product.class);
//        Root<Product> productRoot = query.from(Product.class);
//        query.select(productRoot);
//        em.createQuery(query).getResultList().forEach(Start::printWithKey);


        //*from
//        TypedQuery<Product> query = em.createQuery("from Product ", Product.class);
//        query.getResultList().forEach(Start::printWithKey);

//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Product> query = cb.createQuery(Product.class);
//        query.from(Product.class);
//        em.createQuery(query).getResultList().forEach(Start::printWithKey);


//        List<Animal> animals = em.createQuery("from Animal ", Animal.class).getResultList();
//        animals.forEach(Start::printWithKey);

//        Animal it = Animal.builder()
//                .origin("Нечто")
//                .build();
//        em.persist(it);
//        Bird eagle = Bird.builder()
//                .origin("Eagle")
//                .flyable(true)
//                .growing("Nested")
//                .build();
//
//        Fish shark = Fish.builder()
//                .origin("Shark")
//                .poison(false)
//                .skeleton("Хрящевые")
//                .build();
////
//        em.persist(eagle);
//        em.persist(shark);



//        Post marryChristmas = em.find(Post.class, 6L);
//        printWithKey(marryChristmas);

//        Post newYear = em.find(Post.class, 5L);
//        newYear.getTags().clear();

//
//        em.merge(newYear);
//
//        em.remove(newYear);


//        em.remove(em.find(Post.class, 3L));
//        printWithKey(post);

//        Post newYearPost = Post.builder()
//                .name("Happy New Year")
//                .tags(new HashSet<>())
//                .build();
//
//        Post marryChristmas = Post.builder()
//                .name("Marry Christmas")
//                .tags(new HashSet<>())
//                .build();
//
//        Tag holiday = Tag.builder()
//                .posts(new HashSet<>())
//                .name("Holyday")
//                .build();
//
//        Tag favorite = Tag.builder()
//                .name("Favorite")
//                .posts(new HashSet<>())
//                .build();
//
//        newYearPost.addTag(holiday);
//        newYearPost.addTag(favorite);
//
//        marryChristmas.addTag(holiday);
//
//        em.persist(newYearPost);
//        em.persist(marryChristmas);

//        ProductType comp = em.find(ProductType.class, 1L);
//        ProductTypeRepository rep = new ProductTypeRepository();
//        ProductType comp = rep.find(1L);
//        printWithKey(comp);
//        comp.getProducts().stream().findFirst().get();

//        em.remove(em.find(ProductType.class, 4L));

//        Product apple;
//        Product asus;
//        ProductType comp = ProductType.builder()
//                .name("comp")
//                .products(Set.of(
//                        apple = Product.builder()
//                                .name("Apple")
//                                .cost(2000)
//                                .build(),
//                        asus = Product.builder()
//                                .name("Asus")
//                                .cost(1500)
//                                .build()
//                ))
//                .build();
//        apple.setProductType(comp);
//        asus.setProductType(comp);
//
//        em.persist(comp);


//
//                Car bmw = em.find(Car.class, 1L);
//        bmw.setAudioSystem(AudioSystem.builder()
//                .musicPower(300)
//                .musicName("Sony")
//                .speakers(6)
//                .build());
//        em.persist(bmw);


//        Car bmw = em.find(Car.class, 1L);
//        bmw.setEngine(Engine.DIESEL);
//        em.persist(bmw);


//        Report mfn = Report.builder()
//                .name("Налоги")
//                .type("Штраф")
//                .recipient("MFN")
//                .build();
//
//        Report gai = Report.builder()
//                .name("Оплата")
//                .type("Штраф")
//                .recipient("GAI")
//                .build();
//
//        em.persist(mfn);
//        em.persist(gai);

//        printWithKey(em.find(Report.class, new ReportKey("Налоги", "Штраф")));



//        Car bmw = Car.builder()
//                .model("BMW")
//                .releaseDate(Date.from(LocalDate.of(2000, 11, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .build();
//
//        Car lada = Car.builder()
//                .model("Lada")
//                .releaseDate(Date.from(LocalDate.of(20010, 5, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()))
//                .build();
//        em.persist(bmw);
//        em.persist(lada);




//        Employee entity = em.find(Employee.class, 1);
//        System.out.println("!!! " + employee);
//        Title entity = em.find(Title.class, 3);
//
//        Title entity = new Title()
//                .withName("Директор");

//        City entity = new City()
//                .withName("Могилёв");
//
//        em.persist(entity);
//        printWithKey(entity);

        trx.commit();
        em.close();
    }

    private static void printWithKey(Object obj) {
        System.out.println("!!!" + obj);
    }
}
