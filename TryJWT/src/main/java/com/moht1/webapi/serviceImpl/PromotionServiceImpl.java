package com.moht1.webapi.serviceImpl;

import com.moht1.webapi.model.Product;
import com.moht1.webapi.model.Promotion;
import com.moht1.webapi.model.PromotionDetail;
import com.moht1.webapi.repository.ProductRepository;
import com.moht1.webapi.repository.PromotionDetailRepository;
import com.moht1.webapi.repository.PromotionRepository;
import com.moht1.webapi.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PromotionDetailRepository promotionDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public Promotion findById(Integer id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        if (!promotion.isPresent()) {
            return null;
        }
        return promotion.get();
    }

    @Override
    public List<Promotion> findAllByStatus(String status) {
        List<Promotion> list = null;
        if (status.equals("all"))
            list = promotionRepository.findAllByOrderByIdDesc();

        else if (status.equals("comingsoon"))
            list = promotionRepository.findByStartDateAfterOrderByIdDesc(new Date());

        else if (status.equals("ongoing"))
            list = promotionRepository.findByStartDateBeforeAndFinishDateAfterOrderByIdDesc(new Date(), new Date());

        else
            list = promotionRepository.findByFinishDateBeforeOrderByIdDesc(new Date());

        return list;
    }

    @Override
    public Promotion addPromotion(Promotion promotion) {
        // TODO Auto-generated method stub
        return promotionRepository.save(promotion);
    }

    @Override
    public Promotion editPromotion(Promotion promotion) {
        // TODO Auto-generated method stub
        return promotionRepository.save(promotion);
    }

    @Override
    public void deletePromotion(Promotion promotion) {
        // TODO Auto-generated method stub
        List<PromotionDetail> oldList = promotionDetailRepository.findByPromotion(promotion);

        promotionDetailRepository.deleteByPromotion(promotion);
        promotionRepository.delete(promotion);
    }

    @Override
    public Date convertStringToDate(String dateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = dateTime.split("T")[0];
        String time = dateTime.split("T")[1];
        Date result = null;
        try {
            result = formatter.parse(date + " " + time);
            System.out.println(result);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int getCurrentPromotionByProduct(Product product) {
        List<Promotion> currentPromotion = promotionRepository.findByStartDateBeforeAndFinishDateAfterOrderByIdDesc(new Date(), new Date());

        for (Promotion p : currentPromotion) {
            List<PromotionDetail> promotionDetail = promotionDetailRepository.findByPromotionAndProduct(p, product);
            if (!promotionDetail.isEmpty())
                return promotionDetail.get(0).getPercentage();

        }
        return 0;
    }

    @Override
    public String checkPromotionByProduct(Product product, Date startDate, Date finishDate) {

//		//Case 1: date1 <= startDate <= date2
//		List<Promotion> promotion1 = promotionRepository.findByStartDateBeforeAndFinishDateAfterOrderByIdDesc(startDate, startDate);
//		
//		//Case 1: date1 <= finishDate <= date2
//		List<Promotion> promotion2 = promotionRepository.findByStartDateBeforeAndFinishDateAfterOrderByIdDesc(finishDate, finishDate);
//		
//		//Case 1: date1 <= startDate < finishDate <= date2
//		List<Promotion> promotion3 = promotionRepository.findByStartDateBeforeAndFinishDateAfterOrderByIdDesc(startDate, finishDate);
//		
//		//Case 1: startDate <= date1 < date2 <= finishDate
//		List<Promotion> promotion4 = promotionRepository.findByStartDateAfterAndFinishDateBeforeOrderByIdDesc(startDate, finishDate);
//		
//		promotion1.addAll(promotion2);
//		promotion1.addAll(promotion3);
//		promotion1.addAll(promotion4);

        //Case 1: date1 <= startDate <= date2
        List<Promotion> promotion1 = promotionRepository.findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(startDate, startDate);

        //Case 2: date1 <= finishDate <= date2
        List<Promotion> promotion2 = promotionRepository.findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(finishDate, finishDate);

        //Case 3: date1 <= startDate < finishDate <= date2
        List<Promotion> promotion3 = promotionRepository.findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(startDate, finishDate);

        //Case 4: startDate <= date1 < date2 <= finishDate
        List<Promotion> promotion4 = promotionRepository.findByStartDateGreaterThanEqualAndFinishDateLessThanEqualOrderByIdDesc(startDate, finishDate);

        promotion1.removeAll(promotion2);
        promotion1.addAll(promotion2);
        promotion1.removeAll(promotion3);
        promotion1.addAll(promotion3);
        promotion1.removeAll(promotion4);
        promotion1.addAll(promotion4);

        for (Promotion p : promotion1) {
            List<PromotionDetail> promotionDetail = promotionDetailRepository.findByPromotionAndProduct(p, product);
            if (!promotionDetail.isEmpty())
                return "true";

        }
        return "false";
    }

    @Override
    public List<Promotion> findPromotion(Date startDate, Date finishDate) {
        //Case 1: date1 <= startDate <= date2
        List<Promotion> promotion1 = promotionRepository.findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(startDate, startDate);

        //Case 2: date1 <= finishDate <= date2
        List<Promotion> promotion2 = promotionRepository.findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(finishDate, finishDate);

        //Case 3: date1 <= startDate < finishDate <= date2
        List<Promotion> promotion3 = promotionRepository.findByStartDateLessThanEqualAndFinishDateGreaterThanEqualOrderByIdDesc(startDate, finishDate);

        //Case 4: startDate <= date1 < date2 <= finishDate
        List<Promotion> promotion4 = promotionRepository.findByStartDateGreaterThanEqualAndFinishDateLessThanEqualOrderByIdDesc(startDate, finishDate);

        promotion1.removeAll(promotion2);
        promotion1.addAll(promotion2);
        promotion1.removeAll(promotion3);
        promotion1.addAll(promotion3);
        promotion1.removeAll(promotion4);
        promotion1.addAll(promotion4);
        return promotion1;
    }

}
