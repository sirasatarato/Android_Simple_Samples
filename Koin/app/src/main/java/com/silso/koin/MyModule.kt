package com.silso.koin

import org.koin.dsl.module

// 여기서 Koin DSL을 이용하여 모듈을 만들어줍니다.
val appModule = module {
    single<Repository> { RepositoryImpl() } // 싱글톤으로 생성
    factory { MyPresenter(get()) } // 의존성 주입때마다 인스턴스 생성, get()을 이용하면 위에서 선언된 적절한 클래스가 주입됩니다.
}