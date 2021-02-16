package jp.co.internous.sampleweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import jp.co.internous.sampleweb.model.domain.MstUser;
import jp.co.internous.sampleweb.model.form.UserForm;
import jp.co.internous.sampleweb.model.mapper.MstUserMapper;
import jp.co.internous.sampleweb.model.mapper.TblCartMapper;
import jp.co.internous.sampleweb.model.session.LoginSession;

@RestController
@RequestMapping("/sampleweb/auth")
public class AuthController {
	
	private Gson gson = new Gson();
	
	@Autowired
	private MstUserMapper userMapper;
	
	@Autowired
	private TblCartMapper cartMapper;
	
	@Autowired
	private LoginSession loginSession;
	
	@RequestMapping("/login")
	public String login(@RequestBody UserForm f) {
		MstUser user = userMapper.findByUserNameAndPassword(f.getUserName(), f.getPassword());
		
		int tmpUserId = loginSession.getTmpUserId();
		
		// 仮IDでカート追加されていれば、本ユーザーIDに更新する。
		if (user != null && tmpUserId != 0) {
			//在DB裡面找到tempUserId的紀錄 
			//新增商品到購物車的機能裡面，會有Insert Record To DB的動作，而且放的Id是TempUserId
			
			
			// 沒有login的時候，我有三個紀錄
			// 商品A, tempUserId;123
			// 商品B, tempUserId;123
			// 商品C, tempUserId;123
			
			//use tmpUserId find count in cart
			int count = cartMapper.findCountByUserId(tmpUserId);
		
			// count == 3
			// login之後
			// real userId == 100
			// cartMapper.updateUserId(user.getId(), tmpUserId);
		    // 商品A, tempUserId;100
			// 商品B, tempUserId;100
			// 商品C, tempUserId;100
			
			
			// if tempUserid == 0
			// count == 0，找不到
			
			// if tempUserid != 0
			// count == 0, 找不到
			// count > 0, 找到紀錄了
			
			//如果有紀錄，我現在已經是Login
			//所以要把這些logout之前放到購物車裡面的東西，變成是我的真正userId
			if (count > 0) {
				cartMapper.updateUserId(user.getId(), tmpUserId);
			}
		}
		
		if (user != null) {
			loginSession.setTmpUserId(0);
			loginSession.setLogined(true);
			loginSession.setUserId(user.getId());
			loginSession.setUserName(user.getUserName());
			loginSession.setPassword(user.getPassword());
		} else {
			loginSession.setLogined(false);
			loginSession.setUserId(0);
			loginSession.setUserName(null);
			loginSession.setPassword(null);
		}
		
		return gson.toJson(user);
	}
	
	@RequestMapping("/logout")
	public String logout() {
		loginSession.setTmpUserId(0); 
		loginSession.setLogined(false);
		loginSession.setUserId(0);
		loginSession.setUserName(null);
		loginSession.setPassword(null);
		
		return "";
	}
	
	@RequestMapping("/resetPassword")
	public String resetPassword(@RequestBody UserForm f) {
		String message = "パスワードが再設定されました。";
		String newPassword = f.getNewPassword();
		String newPasswordConfirm = f.getNewPasswordConfirm();
		
		MstUser user = userMapper.findByUserNameAndPassword(f.getUserName(), f.getPassword());
		if (user == null) {
			return "現在のパスワードが正しくありません。";
		}
		
		if (user.getPassword().equals(newPassword)) {
			return "現在のパスワードと同一文字列が入力されました。";
		}
		
		if (!newPassword.equals(newPasswordConfirm)) {
			return "新パスワードと確認用パスワードが一致しません。";
		}
		// mst_userとloginSessionのパスワードを更新する
		userMapper.updatePassword(user.getUserName(), f.getNewPassword());
		loginSession.setPassword(f.getNewPassword());
		
		
		return message;
	}
}
