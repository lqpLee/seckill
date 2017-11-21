package org.seckill.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by liqiangpeng on 2017/4/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> seckillList = seckillService.getSeckillList();
        logger.info("seckillList={}", seckillList);
    }

    @Test
    public void getSeckillById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getSeckillById(id);
        logger.info("seckill={}", seckill);
    }


    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void seckillExecute() throws Exception {
        long id = 1000;
        long phone = 13122090181L;
        String md5 = "e5e1cbbb9d6cefdfd0b89b7086feea7e";

        SeckillExecution execution = seckillService.seckillExecute(id, phone, md5);
        logger.info("Seckill={}", execution);
    }

    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1000;
        long phone = 13122190181L;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        if (exposer.isExposed()) {
            try {
                SeckillExecution execution = seckillService.seckillExecute(id, phone, exposer.getMd5());
                logger.info("result={}", execution);
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }
        //15:37:28.690 [main] INFO  o.s.s.impl.SeckillServiceImplTest - result=
        // SeckillExecution{seckillId=1000, state=1, stateInfo='秒杀成功',
        // successKilled=SuccessKilled{seckillId=1000, phone=13122090181, state=0, createTime=Sat Feb 25 05:37:28 CST 2017}}

        //第二次点击会提示重复秒杀
        //15:38:05.222 [main] ERROR o.s.s.impl.SeckillServiceImplTest - Seckill repeated

    }

}