/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.komuniti.controller;

import cz.komuniti.model.entity.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author novakst6
 */
@Controller
public class HomeController extends TemplateController {

    @RequestMapping(value ={"index.htm"}, method = RequestMethod.GET)
    public String index() throws Exception {
        List<UserRoleEntity> list = getUserRoleManager().findAll();
        if (list.isEmpty()) {
            UserRoleEntity roleUser = new UserRoleEntity("ROLE_USER");
            UserRoleEntity roleAdmin = new UserRoleEntity("ROLE_ADMIN");
            UserRoleEntity roleAdminObsahu = new UserRoleEntity("ROLE_ADMIN_CONTENT");
            UserRoleEntity roleAdminCentrum = new UserRoleEntity("ROLE_ADMIN_CENTER");
            getUserRoleManager().add(roleUser);
            getUserRoleManager().add(roleAdmin);
            getUserRoleManager().add(roleAdminObsahu);
            getUserRoleManager().add(roleAdminCentrum);

            RegionEntity r0 = new RegionEntity();
            r0.setName("Jihočeský");
            getRegionManager().add(r0);
            RegionEntity r1 = new RegionEntity();
            r1.setName("České Budějovice");
            r1.setParent(r0);
            getRegionManager().add(r1);
            RegionEntity r2 = new RegionEntity();
            r2.setName("Český Krumlov");
            r2.setParent(r0);
            getRegionManager().add(r2);
            RegionEntity r3 = new RegionEntity();
            r3.setName("Jindřichův Hradec");
            r3.setParent(r0);
            getRegionManager().add(r3);
            RegionEntity r4 = new RegionEntity();
            r4.setName("Písek");
            r4.setParent(r0);
            getRegionManager().add(r4);
            RegionEntity r5 = new RegionEntity();
            r5.setName("Prachatice");
            r5.setParent(r0);
            getRegionManager().add(r5);
            RegionEntity r6 = new RegionEntity();
            r6.setName("Strakonice");
            r6.setParent(r0);
            getRegionManager().add(r6);
            RegionEntity r7 = new RegionEntity();
            r7.setName("Tábor");
            r7.setParent(r0);
            getRegionManager().add(r7);

            RegionEntity r8 = new RegionEntity();
            r8.setName("Jihomoravský");
            getRegionManager().add(r8);
            RegionEntity r9 = new RegionEntity();
            r9.setName("Blansko");
            r9.setParent(r8);
            getRegionManager().add(r9);
            RegionEntity r10 = new RegionEntity();
            r10.setName("Brno-město");
            r10.setParent(r8);
            getRegionManager().add(r10);
            RegionEntity r11 = new RegionEntity();
            r11.setName("Brno-venkov");
            r11.setParent(r8);
            getRegionManager().add(r11);
            RegionEntity r12 = new RegionEntity();
            r12.setName("Břeclav");
            r12.setParent(r8);
            getRegionManager().add(r12);
            RegionEntity r13 = new RegionEntity();
            r13.setName("Hodonín");
            r13.setParent(r8);
            getRegionManager().add(r13);
            RegionEntity r14 = new RegionEntity();
            r14.setName("Vyškov");
            r14.setParent(r8);
            getRegionManager().add(r14);
            RegionEntity r15 = new RegionEntity();
            r15.setName("Znojmo");
            r15.setParent(r8);
            getRegionManager().add(r15);

            RegionEntity r16 = new RegionEntity();
            r16.setName("Karlovarský");
            getRegionManager().add(r16);
            RegionEntity r17 = new RegionEntity();
            r17.setName("Cheb");
            r17.setParent(r16);
            getRegionManager().add(r17);
            RegionEntity r18 = new RegionEntity();
            r18.setName("Karlovy Vary");
            r18.setParent(r16);
            getRegionManager().add(r18);
            RegionEntity r19 = new RegionEntity();
            r19.setName("Sokolov");
            r19.setParent(r16);
            getRegionManager().add(r19);

            RegionEntity r20 = new RegionEntity();
            r20.setName("Královéhradecký");
            getRegionManager().add(r20);
            RegionEntity r21 = new RegionEntity();
            r21.setName("Hradec Králové");
            r21.setParent(r20);
            getRegionManager().add(r21);
            RegionEntity r22 = new RegionEntity();
            r22.setName("Jičín");
            r22.setParent(r20);
            getRegionManager().add(r22);
            RegionEntity r23 = new RegionEntity();
            r23.setName("Náchod");
            r23.setParent(r20);
            getRegionManager().add(r23);
            RegionEntity r24 = new RegionEntity();
            r24.setName("Rychnov nad Kněžnou");
            r24.setParent(r20);
            getRegionManager().add(r24);
            RegionEntity r25 = new RegionEntity();
            r25.setName("Trutnov");
            r25.setParent(r20);
            getRegionManager().add(r25);

            RegionEntity r26 = new RegionEntity();
            r26.setName("Liberecký");
            getRegionManager().add(r26);
            RegionEntity r27 = new RegionEntity();
            r27.setName("Česká Lípa");
            r27.setParent(r26);
            getRegionManager().add(r27);
            RegionEntity r28 = new RegionEntity();
            r28.setName("Jablonec nad Nisou");
            r28.setParent(r26);
            getRegionManager().add(r28);
            RegionEntity r29 = new RegionEntity();
            r29.setName("Liberec");
            r29.setParent(r26);
            getRegionManager().add(r29);
            RegionEntity r30 = new RegionEntity();
            r30.setName("Semily");
            r30.setParent(r26);
            getRegionManager().add(r30);

            RegionEntity r31 = new RegionEntity();
            r31.setName("Moravskoslezský");
            getRegionManager().add(r31);
            RegionEntity r32 = new RegionEntity();
            r32.setName("Bruntál");
            r32.setParent(r31);
            getRegionManager().add(r32);
            RegionEntity r33 = new RegionEntity();
            r33.setName("Frýdek-místek");
            r33.setParent(r31);
            getRegionManager().add(r33);
            RegionEntity r34 = new RegionEntity();
            r34.setName("Karviná");
            r34.setParent(r31);
            getRegionManager().add(r34);
            RegionEntity r35 = new RegionEntity();
            r35.setName("Nový Jičín");
            r35.setParent(r31);
            getRegionManager().add(r35);
            RegionEntity r36 = new RegionEntity();
            r36.setName("Opava");
            r36.setParent(r31);
            getRegionManager().add(r36);
            RegionEntity r37 = new RegionEntity();
            r37.setName("Ostrava-město");
            r37.setParent(r31);
            getRegionManager().add(r37);

            RegionEntity r38 = new RegionEntity();
            r38.setName("Olomoucký");
            getRegionManager().add(r38);
            RegionEntity r39 = new RegionEntity();
            r39.setName("Jeseník");
            r39.setParent(r38);
            getRegionManager().add(r39);
            RegionEntity r40 = new RegionEntity();
            r40.setName("Olomouc");
            r40.setParent(r38);
            getRegionManager().add(r40);
            RegionEntity r41 = new RegionEntity();
            r41.setName("Prostějov");
            r41.setParent(r38);
            getRegionManager().add(r41);
            RegionEntity r42 = new RegionEntity();
            r42.setName("Přerov");
            r42.setParent(r38);
            getRegionManager().add(r42);
            RegionEntity r43 = new RegionEntity();
            r43.setName("Přerov");
            r43.setParent(r38);
            getRegionManager().add(r43);

            RegionEntity r44 = new RegionEntity();
            r44.setName("Pardubický");
            getRegionManager().add(r44);
            RegionEntity r45 = new RegionEntity();
            r45.setName("Chrudim");
            r45.setParent(r44);
            getRegionManager().add(r45);
            RegionEntity r46 = new RegionEntity();
            r46.setName("Pardubice");
            r46.setParent(r44);
            getRegionManager().add(r46);
            RegionEntity r47 = new RegionEntity();
            r47.setName("Svitavy");
            r47.setParent(r44);
            getRegionManager().add(r47);
            RegionEntity r48 = new RegionEntity();
            r48.setName("Ústí nad Orlicí");
            r48.setParent(r44);
            getRegionManager().add(r48);

            RegionEntity r49 = new RegionEntity();
            r49.setName("Plzeňský");
            getRegionManager().add(r49);
            RegionEntity r50 = new RegionEntity();
            r50.setName("Domažlice");
            r50.setParent(r49);
            getRegionManager().add(r50);
            RegionEntity r51 = new RegionEntity();
            r51.setName("Klatovy");
            r51.setParent(r49);
            getRegionManager().add(r51);
            RegionEntity r52 = new RegionEntity();
            r52.setName("Plzeň-jih");
            r52.setParent(r49);
            getRegionManager().add(r52);
            RegionEntity r53 = new RegionEntity();
            r53.setName("Plzeň-město");
            r53.setParent(r49);
            getRegionManager().add(r53);
            RegionEntity r54 = new RegionEntity();
            r54.setName("Plzeň-sever");
            r54.setParent(r49);
            getRegionManager().add(r54);
            RegionEntity r55 = new RegionEntity();
            r55.setName("Rokycany");
            r55.setParent(r49);
            getRegionManager().add(r55);
            RegionEntity r56 = new RegionEntity();
            r56.setName("Tachov");
            r56.setParent(r49);
            getRegionManager().add(r56);

            RegionEntity r57 = new RegionEntity();
            r57.setName("Středočeský");
            getRegionManager().add(r57);
            RegionEntity r58 = new RegionEntity();
            r58.setName("Benešov");
            r58.setParent(r57);
            getRegionManager().add(r58);
            RegionEntity r59 = new RegionEntity();
            r59.setName("Beroun");
            r59.setParent(r57);
            getRegionManager().add(r59);
            RegionEntity r60 = new RegionEntity();
            r60.setName("Kladno");
            r60.setParent(r57);
            getRegionManager().add(r60);
            RegionEntity r61 = new RegionEntity();
            r61.setName("Kolín");
            r61.setParent(r57);
            getRegionManager().add(r61);
            RegionEntity r62 = new RegionEntity();
            r62.setName("Kutná Hora");
            r62.setParent(r57);
            getRegionManager().add(r62);
            RegionEntity r63 = new RegionEntity();
            r63.setName("Mladá Boleslav");
            r63.setParent(r57);
            getRegionManager().add(r63);
            RegionEntity r64 = new RegionEntity();
            r64.setName("Mělník");
            r64.setParent(r57);
            getRegionManager().add(r64);
            RegionEntity r65 = new RegionEntity();
            r65.setName("Nymburk");
            r65.setParent(r57);
            getRegionManager().add(r65);
            RegionEntity r66 = new RegionEntity();
            r66.setName("Praha-východ");
            r66.setParent(r57);
            getRegionManager().add(r66);
            RegionEntity r67 = new RegionEntity();
            r67.setName("Praha-západ");
            r67.setParent(r57);
            getRegionManager().add(r67);
            RegionEntity r68 = new RegionEntity();
            r68.setName("Příbram");
            r68.setParent(r57);
            getRegionManager().add(r68);
            RegionEntity r69 = new RegionEntity();
            r69.setName("Rakovník");
            r69.setParent(r57);
            getRegionManager().add(r69);

            RegionEntity r70 = new RegionEntity();
            r70.setName("Ústecký");
            getRegionManager().add(r70);
            RegionEntity r71 = new RegionEntity();
            r71.setName("Děčín");
            r71.setParent(r70);
            getRegionManager().add(r71);
            RegionEntity r72 = new RegionEntity();
            r72.setName("Chomutov");
            r72.setParent(r70);
            getRegionManager().add(r72);
            RegionEntity r73 = new RegionEntity();
            r73.setName("Litoměřice");
            r73.setParent(r70);
            getRegionManager().add(r73);
            RegionEntity r74 = new RegionEntity();
            r74.setName("Louny");
            r74.setParent(r70);
            getRegionManager().add(r74);
            RegionEntity r75 = new RegionEntity();
            r75.setName("Most");
            r75.setParent(r70);
            getRegionManager().add(r75);
            RegionEntity r76 = new RegionEntity();
            r76.setName("Teplice");
            r76.setParent(r70);
            getRegionManager().add(r76);
            RegionEntity r77 = new RegionEntity();
            r77.setName("Ústí nad Labem");
            r77.setParent(r70);
            getRegionManager().add(r77);

            RegionEntity r78 = new RegionEntity();
            r78.setName("Vysočina");
            getRegionManager().add(r78);
            RegionEntity r79 = new RegionEntity();
            r79.setName("Havlíčkův Brod");
            r79.setParent(r78);
            getRegionManager().add(r79);
            RegionEntity r80 = new RegionEntity();
            r80.setName("Jihlava");
            r80.setParent(r78);
            getRegionManager().add(r80);
            RegionEntity r81 = new RegionEntity();
            r81.setName("Pelhřimov");
            r81.setParent(r78);
            getRegionManager().add(r81);
            RegionEntity r82 = new RegionEntity();
            r82.setName("Třebíč");
            r82.setParent(r78);
            getRegionManager().add(r82);
            RegionEntity r83 = new RegionEntity();
            r83.setName("Žďár nad Sázavou");
            r83.setParent(r78);
            getRegionManager().add(r83);

            RegionEntity r84 = new RegionEntity();
            r84.setName("Zlínský");
            getRegionManager().add(r84);
            RegionEntity r85 = new RegionEntity();
            r85.setName("Kroměříž");
            r85.setParent(r84);
            getRegionManager().add(r85);
            RegionEntity r86 = new RegionEntity();
            r86.setName("Uherské Hradiště");
            r86.setParent(r84);
            getRegionManager().add(r86);
            RegionEntity r87 = new RegionEntity();
            r87.setName("Vsetín");
            r87.setParent(r84);
            getRegionManager().add(r87);
            RegionEntity r88 = new RegionEntity();
            r88.setName("Zlín");
            r88.setParent(r84);
            getRegionManager().add(r88);


            CenterEntity center1 = new CenterEntity();
            center1.setName("Unie center pro rodinu a komunitu");
            center1.setRegion(r7);
            center1.setInfo("Some Info");
            getCenterManager().add(center1);

            String encodePassword = getStandardPassworgEncoder().encode(
                    "11111");
            UserEntity user1 = new UserEntity("user@user.cz", encodePassword);
            Set<UserRoleEntity> roles = new HashSet<UserRoleEntity>();
            roles.add(roleUser);
            roles.add(roleAdmin);
            user1.setRoles(roles);
            user1.setActive(Boolean.TRUE);
            user1.setDeleted(Boolean.FALSE);
            user1.setRegion(r8);
            user1.setCentrum(center1);
            user1.setGoogleName("Stenlik stenlik");
            getUserManager().add(user1);

            encodePassword = getStandardPassworgEncoder().encode(
                    "11111");
            UserEntity user2 = new UserEntity("admin@admin.cz", encodePassword);
            Set<UserRoleEntity> roles2 = new HashSet<UserRoleEntity>();
            roles2.add(roleUser);
            roles2.add(roleAdmin);
            user2.setRoles(roles2);
            user2.setRegion(r5);
            user2.setActive(Boolean.TRUE);
            user2.setDeleted(Boolean.FALSE);
            user2.setCentrum(center1);
            user2.setGoogleName("Admin admin");
            getUserManager().add(user2);

            MessageEntity msg1 = new MessageEntity();
            msg1.setAuthor(user1);
            msg1.setRecipient(user2);
            msg1.setOwner(user1);
            msg1.setReaded(Boolean.FALSE);
            msg1.setSendDate(new Date(System.currentTimeMillis()));
            msg1.setSubject("SUBJECT >> ");
            msg1.setText("Test text, Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text");
            msg1.setDeleted(Boolean.FALSE);
            getMessageManager().add(msg1);

            MessageEntity msg2 = new MessageEntity();
            msg2.setAuthor(user1);
            msg2.setRecipient(user2);
            msg2.setOwner(user2);
            msg2.setReaded(Boolean.FALSE);
            msg2.setSendDate(new Date(System.currentTimeMillis()));
            msg2.setSubject("SUBJECT >> ");
            msg2.setText("Test text, Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text Test text");
            msg2.setDeleted(Boolean.FALSE);
            getMessageManager().add(msg2);

            TagEntity t0 = new TagEntity();
            t0.setName("Category 1");
            t0.setParent(null);
            getTagManager().add(t0);

            TagEntity t1 = new TagEntity();
            t1.setName("Category 1.1");
            t1.setParent(t0);
            getTagManager().add(t1);

            TagEntity t2 = new TagEntity();
            t2.setName("Category 1.2");
            t2.setParent(t0);
            getTagManager().add(t2);

            TagEntity t3 = new TagEntity();
            t3.setName("Category 2");
            t3.setParent(null);
            getTagManager().add(t3);

            TagEntity t4 = new TagEntity();
            t4.setName("Category 2.1");
            t4.setParent(t3);
            getTagManager().add(t4);

            TagEntity t5 = new TagEntity();
            t5.setName("Category 2.2");
            t5.setParent(t3);
            getTagManager().add(t5);
            
            TagEntity t6 = new TagEntity();
            t6.setName("Category 3");
            t6.setParent(null);
            getTagManager().add(t6);
            
            TagEntity t7 = new TagEntity();
            t7.setName("Category 3.1");
            t7.setParent(t6);
            getTagManager().add(t7);

            TagEntity t8 = new TagEntity();
            t8.setName("Category 3.2");
            t8.setParent(t6);
            getTagManager().add(t8);

//            ItemEntity i0 = new ItemEntity();
//            i0.setName("Item 1");
//            i0.setDescription("Description of item.");
//            i0.setText("<a href=\"#\">Anchor</a>");
//            i0.getTags().add(t5);
//            i0.getTags().add(t0);
//            i0.setActive(Boolean.TRUE);
//            i0.setDeleted(Boolean.FALSE);
//            i0.setCreatedByUser(Boolean.FALSE);
//            getItemManager().add(i0);
//
//            ItemEntity i1 = new ItemEntity();
//            i1.setName("Item 2");
//            i1.setDescription("Description of item.");
//            i1.setText("<a href=\"#\">Anchor</a>");
//            i1.getTags().add(t3);
//            i1.getTags().add(t4);
//            i1.setActive(Boolean.TRUE);
//            i1.setDeleted(Boolean.FALSE);
//            i1.setCreatedByUser(Boolean.FALSE);
//            getItemManager().add(i1);

//            for (int i = 0; i < 60; i++) {
//                ItemEntity i2 = new ItemEntity();
//                i2.setName("Item " + (i + 3));
//                i2.setDescription("Description of item" + i);
//                i2.setText("Testing description"+i);
//                i2.getTags().add(t3);
//                i2.getTags().add(t4);
//                i2.getTags().add(t5);
//                i2.getTags().add(t0);
//                i2.getTags().add(t1);
//                i2.getTags().add(t2);
//                i2.setActive(Boolean.TRUE);
//                i2.setDeleted(Boolean.FALSE);
//                i2.setCreatedByUser(Boolean.TRUE);
//                getItemManager().add(i2);
//
//            }

            OfferTagEntity ot1 = new OfferTagEntity();
            ot1.setName("Prodám");
            getOfferTagManager().add(ot1);

            OfferTagEntity ot2 = new OfferTagEntity();
            ot2.setName("Koupím");
            getOfferTagManager().add(ot2);

            OfferTagEntity ot3 = new OfferTagEntity();
            ot3.setName("Vyměním");
            getOfferTagManager().add(ot3);

            OfferTagEntity ot4 = new OfferTagEntity();
            ot4.setName("Daruji");
            getOfferTagManager().add(ot4);

            OfferTagEntity ot5 = new OfferTagEntity();
            ot5.setName("Půjčím");
            getOfferTagManager().add(ot5);

//            OfferEntity of1 = new OfferEntity();
//            of1.setActive(Boolean.TRUE);
//            of1.setAuthor(user1);
//            of1.setDeleted(Boolean.FALSE);
//            of1.setInsertDate(new Timestamp(System.currentTimeMillis()));
//            of1.setItem(i1);
//            of1.getTags().add(ot5);
//            of1.getTags().add(ot3);
//            of1.getTags().add(ot1);
//            of1.setText("Inzerát 1");
//            of1.setTitle("Testovácí inzerát 1");
//            of1.setEdited(Boolean.FALSE);
//            getOfferManager().add(of1);
//
//            OfferEntity of2 = new OfferEntity();
//            of2.setActive(Boolean.TRUE);
//            of2.setAuthor(user1);
//            of2.setDeleted(Boolean.FALSE);
//            of2.setInsertDate(new Timestamp(System.currentTimeMillis()));
//            of2.setItem(i0);
//            of2.getTags().add(ot5);
//            of2.getTags().add(ot2);
//            of2.getTags().add(ot1);
//            of2.setText("Inzerát 2");
//            of2.setTitle("Testovácí inzerát 2");
//            of2.setEdited(Boolean.FALSE);
//            getOfferManager().add(of2);

//            HelpEntity h1 = new HelpEntity();
//            h1.setInsertDate(new Timestamp(System.currentTimeMillis()));
//            h1.setTitle("Help 1");
//            h1.setText("TEXT");
//            h1.setAuthor(user2);
//            getHelpManager().add(h1);
//
//            HelpEntity h2 = new HelpEntity();
//            h2.setInsertDate(new Timestamp(System.currentTimeMillis()));
//            h2.setTitle("Help 2");
//            h2.setText("TEXT");
//            h2.setAuthor(user1);
//            getHelpManager().add(h2);

//            for (int i = 0; i < 20; i++) {
//
//                ArticleEntity ar1 = new ArticleEntity();
//                ar1.setAuthor(user2);
//                ar1.setDateOfInsert(new Timestamp(System.currentTimeMillis()));
//                if (i % 2 == 0) {
//                    ar1.setDeleted(Boolean.FALSE);
//                } else {
//                    ar1.setDeleted(Boolean.FALSE);
//                }
//                ar1.setEdited(Boolean.FALSE);
//                ar1.setText("Testovací text článku jedna. " + i);
//                ar1.setTitle("Článek " + i);
//                getArticleManager().add(ar1);
//            }
            
            AppConfigEntity app = new AppConfigEntity();
            getAppConfManager().add(app);
        }
        return "index";
    }

    @RequestMapping(value="randomItem.htm",method= RequestMethod.GET)
    public String randomItem(@RequestParam(value="count") Integer count) throws Exception{
        if(isParameterNull(count)){
            return "redirect:index.htm";
        }
        
        Random r = new Random();
        List<TagEntity> tags = getTagManager().findAll();
               
        for (int i = 0; i < count; i++) {
            ItemEntity item = new ItemEntity();
            item.setName(getPasswordGenerator().getPassword());
            item.setDescription("Description of item. "+getPasswordGenerator().getPassword());
            item.setText("Description"+i);
            for (int j = 0; j < r.nextInt(4) + 1; j++) {
                item.getTags().add(tags.get(r.nextInt(tags.size())));              
            }
            item.setActive(Boolean.TRUE);
            item.setDeleted(Boolean.FALSE);
            item.setCreatedByUser(Boolean.FALSE);
            getItemManager().add(item);            
        }
        return "redirect:index.htm";
    }
    
    @RequestMapping(value="randomOffer.htm",method= RequestMethod.GET)
    public String randomOffer(@RequestParam(value="count") Integer count) throws Exception{
        if(isParameterNull(count)){
            return "redirect:index.htm";
        }
        
        Random r = new Random();
        List<OfferTagEntity> offerTags = getOfferTagManager().findAll();
        List<ItemEntity> items = getItemManager().findAll();
        List<UserEntity> users = getUserManager().findAll();
        
        if(items.isEmpty()){
            getInfoMessages().setWarnMessage("Seznam zbozi je prazdny.");
            return "redirect:index.htm";
        }
        
        if(users.isEmpty()){
            getInfoMessages().setWarnMessage("Seznam uzivatelu je prazdny.");
            return "redirect:index.htm";
        }
        
        for (int i = 0; i < count; i++) {
            OfferEntity offer = new OfferEntity();
            offer.setActive(Boolean.TRUE);
            offer.setAuthor(users.get(r.nextInt(users.size())));
            offer.setDeleted(Boolean.FALSE);
            offer.setInsertDate(new Timestamp(System.currentTimeMillis()));
            offer.setItem(items.get(r.nextInt(items.size())));
            for (int j = 0; j < r.nextInt(3)+ 1; j++) {
                offer.getTags().add(offerTags.get(r.nextInt(offerTags.size())));               
            }
            offer.setText("Description"+i);
            offer.setTitle(getPasswordGenerator().getPassword());
            offer.setEdited(Boolean.FALSE);
            getOfferManager().add(offer);            
        }
        return "redirect:index.htm";
    }
    
}
