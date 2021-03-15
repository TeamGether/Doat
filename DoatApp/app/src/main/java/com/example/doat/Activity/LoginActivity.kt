package com.example.doat.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.doat.R
import com.example.doat.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val callback : (OAuthToken?, Throwable?) -> Unit = {token, error ->
            if (error != null) {
                when(error.toString()) {
                    AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱입니다.", Toast.LENGTH_SHORT).show()
                    }
                    InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태입니다.", Toast.LENGTH_SHORT).show()
                    }
                    InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID 입니다.",Toast.LENGTH_SHORT).show()
                    }
                    Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않습니다. (키 해시 오류)",Toast.LENGTH_SHORT).show()
                    }
                    ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러입니다.",Toast.LENGTH_SHORT).show()
                    }
                    Unauthorized.toString() -> {
                        Toast.makeText(this, "앱 요청 권한이 없습니다.",Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "에러 입니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if(token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.kakaoLoginBtn.setOnClickListener {
            LoginClient.instance.run {
                if(LoginClient.instance.isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    LoginClient.instance.loginWithKakaoTalk(this@LoginActivity, callback = callback)
                } else {
                    LoginClient.instance.loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }

        }

    }


}