package com.malakezzat.foodplanner.model.data;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.malakezzat.foodplanner.model.local.MealDB;
import com.malakezzat.foodplanner.model.local.MealDBWeek;

public class Meal implements Data, Parcelable {
    public String idMeal;
    public String strMeal;
    public String strDrinkAlternate;
    public String strCategory;
    public String strArea;
    public String strInstructions;
    public String strMealThumb;
    public String strTags;
    public String strYoutube;
    public String strIngredient1;
    public String strIngredient2;
    public String strIngredient3;
    public String strIngredient4;
    public String strIngredient5;
    public String strIngredient6;
    public String strIngredient7;
    public String strIngredient8;
    public String strIngredient9;
    public String strIngredient10;
    public String strIngredient11;
    public String strIngredient12;
    public String strIngredient13;
    public String strIngredient14;
    public String strIngredient15;
    public String strIngredient16;
    public String strIngredient17;
    public String strIngredient18;
    public String strIngredient19;
    public String strIngredient20;
    public String strMeasure1;
    public String strMeasure2;
    public String strMeasure3;
    public String strMeasure4;
    public String strMeasure5;
    public String strMeasure6;
    public String strMeasure7;
    public String strMeasure8;
    public String strMeasure9;
    public String strMeasure10;
    public String strMeasure11;
    public String strMeasure12;
    public String strMeasure13;
    public String strMeasure14;
    public String strMeasure15;
    public String strMeasure16;
    public String strMeasure17;
    public String strMeasure18;
    public String strMeasure19;
    public String strMeasure20;
    public String strSource;
    public String strImageSource;
    public String strCreativeCommonsConfirmed;
    public String dateModified;
    public String idIngredient;
    public String strIngredient;
    public String strDescription;
    public String strType;
    public String dateAndTime;
    public boolean isFav;
    public String date;
    public String day;

    @NonNull
    @Override
    public String toString() {
        return "Meal{" +
                "idMeal='" + idMeal + '\'' +
                ", strMeal='" + strMeal + '\'' +
                ", strDrinkAlternate=" + strDrinkAlternate +
                ", strCategory='" + strCategory + '\'' +
                ", strArea='" + strArea + '\'' +
                ", strInstructions='" + strInstructions + '\'' +
                ", strMealThumb='" + strMealThumb + '\'' +
                ", strTags=" + strTags +
                ", strYoutube='" + strYoutube + '\'' +
                ", strIngredient1='" + strIngredient1 + '\'' +
                ", strIngredient2='" + strIngredient2 + '\'' +
                ", strIngredient3='" + strIngredient3 + '\'' +
                ", strIngredient4='" + strIngredient4 + '\'' +
                ", strIngredient5='" + strIngredient5 + '\'' +
                ", strIngredient6='" + strIngredient6 + '\'' +
                ", strIngredient7='" + strIngredient7 + '\'' +
                ", strIngredient8='" + strIngredient8 + '\'' +
                ", strIngredient9='" + strIngredient9 + '\'' +
                ", strIngredient10='" + strIngredient10 + '\'' +
                ", strIngredient11='" + strIngredient11 + '\'' +
                ", strIngredient12='" + strIngredient12 + '\'' +
                ", strIngredient13='" + strIngredient13 + '\'' +
                ", strIngredient14='" + strIngredient14 + '\'' +
                ", strIngredient15='" + strIngredient15 + '\'' +
                ", strIngredient16='" + strIngredient16 + '\'' +
                ", strIngredient17='" + strIngredient17 + '\'' +
                ", strIngredient18='" + strIngredient18 + '\'' +
                ", strIngredient19='" + strIngredient19 + '\'' +
                ", strIngredient20='" + strIngredient20 + '\'' +
                ", strMeasure1='" + strMeasure1 + '\'' +
                ", strMeasure2='" + strMeasure2 + '\'' +
                ", strMeasure3='" + strMeasure3 + '\'' +
                ", strMeasure4='" + strMeasure4 + '\'' +
                ", strMeasure5='" + strMeasure5 + '\'' +
                ", strMeasure6='" + strMeasure6 + '\'' +
                ", strMeasure7='" + strMeasure7 + '\'' +
                ", strMeasure8='" + strMeasure8 + '\'' +
                ", strMeasure9='" + strMeasure9 + '\'' +
                ", strMeasure10='" + strMeasure10 + '\'' +
                ", strMeasure11='" + strMeasure11 + '\'' +
                ", strMeasure12='" + strMeasure12 + '\'' +
                ", strMeasure13='" + strMeasure13 + '\'' +
                ", strMeasure14='" + strMeasure14 + '\'' +
                ", strMeasure15='" + strMeasure15 + '\'' +
                ", strMeasure16='" + strMeasure16 + '\'' +
                ", strMeasure17='" + strMeasure17 + '\'' +
                ", strMeasure18='" + strMeasure18 + '\'' +
                ", strMeasure19='" + strMeasure19 + '\'' +
                ", strMeasure20='" + strMeasure20 + '\'' +
                ", strSource='" + strSource + '\'' +
                ", strImageSource=" + strImageSource +
                ", strCreativeCommonsConfirmed=" + strCreativeCommonsConfirmed +
                ", dateModified=" + dateModified +
                ", idIngredient='" + idIngredient + '\'' +
                ", strIngredient='" + strIngredient + '\'' +
                ", strDescription='" + strDescription + '\'' +
                ", strType=" + strType + '\'' +
                ", isFav=" + isFav + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", date=" + date +
                ", day=" + day +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(idMeal);
        dest.writeString(strMeal);
        dest.writeValue(strDrinkAlternate);
        dest.writeString(strCategory);
        dest.writeString(strArea);
        dest.writeString(strInstructions);
        dest.writeString(strMealThumb);
        dest.writeValue(strTags);
        dest.writeString(strYoutube);
        dest.writeString(strIngredient1);
        dest.writeString(strIngredient2);
        dest.writeString(strIngredient3);
        dest.writeString(strIngredient4);
        dest.writeString(strIngredient5);
        dest.writeString(strIngredient6);
        dest.writeString(strIngredient7);
        dest.writeString(strIngredient8);
        dest.writeString(strIngredient9);
        dest.writeString(strIngredient10);
        dest.writeString(strIngredient11);
        dest.writeString(strIngredient12);
        dest.writeString(strIngredient13);
        dest.writeString(strIngredient14);
        dest.writeString(strIngredient15);
        dest.writeString(strIngredient16);
        dest.writeString(strIngredient17);
        dest.writeString(strIngredient18);
        dest.writeString(strIngredient19);
        dest.writeString(strIngredient20);
        dest.writeString(strMeasure1);
        dest.writeString(strMeasure2);
        dest.writeString(strMeasure3);
        dest.writeString(strMeasure4);
        dest.writeString(strMeasure5);
        dest.writeString(strMeasure6);
        dest.writeString(strMeasure7);
        dest.writeString(strMeasure8);
        dest.writeString(strMeasure9);
        dest.writeString(strMeasure10);
        dest.writeString(strMeasure11);
        dest.writeString(strMeasure12);
        dest.writeString(strMeasure13);
        dest.writeString(strMeasure14);
        dest.writeString(strMeasure15);
        dest.writeString(strMeasure16);
        dest.writeString(strMeasure17);
        dest.writeString(strMeasure18);
        dest.writeString(strMeasure19);
        dest.writeString(strMeasure20);
        dest.writeString(strSource);
        dest.writeValue(strImageSource);
        dest.writeValue(strCreativeCommonsConfirmed);
        dest.writeValue(dateModified);
        dest.writeString(dateAndTime);
        dest.writeString(idIngredient);
        dest.writeString(strIngredient);
        dest.writeString(strDescription);
        dest.writeValue(strType);
        dest.writeByte((byte) (isFav ? 1 : 0));
        dest.writeString(date);
        dest.writeString(day);
    }
    public Meal() {
    }

    public Meal(Parcel in) {
        idMeal = in.readString();
        strMeal = in.readString();
        strDrinkAlternate = in.readString();
        strCategory = in.readString();
        strArea = in.readString();
        strInstructions = in.readString();
        strMealThumb = in.readString();
        strTags = in.readString();
        strYoutube = in.readString();
        strIngredient1 = in.readString();
        strIngredient2 = in.readString();
        strIngredient3 = in.readString();
        strIngredient4 = in.readString();
        strIngredient5 = in.readString();
        strIngredient6 = in.readString();
        strIngredient7 = in.readString();
        strIngredient8 = in.readString();
        strIngredient9 = in.readString();
        strIngredient10 = in.readString();
        strIngredient11 = in.readString();
        strIngredient12 = in.readString();
        strIngredient13 = in.readString();
        strIngredient14 = in.readString();
        strIngredient15 = in.readString();
        strIngredient16 = in.readString();
        strIngredient17 = in.readString();
        strIngredient18 = in.readString();
        strIngredient19 = in.readString();
        strIngredient20 = in.readString();
        strMeasure1 = in.readString();
        strMeasure2 = in.readString();
        strMeasure3 = in.readString();
        strMeasure4 = in.readString();
        strMeasure5 = in.readString();
        strMeasure6 = in.readString();
        strMeasure7 = in.readString();
        strMeasure8 = in.readString();
        strMeasure9 = in.readString();
        strMeasure10 = in.readString();
        strMeasure11 = in.readString();
        strMeasure12 = in.readString();
        strMeasure13 = in.readString();
        strMeasure14 = in.readString();
        strMeasure15 = in.readString();
        strMeasure16 = in.readString();
        strMeasure17 = in.readString();
        strMeasure18 = in.readString();
        strMeasure19 = in.readString();
        strMeasure20 = in.readString();
        strSource = in.readString();
        strImageSource = in.readString();
        strCreativeCommonsConfirmed = in.readString();
        dateModified = in.readString();
        dateAndTime = in.readString();
        idIngredient = in.readString();
        strIngredient = in.readString();
        strDescription = in.readString();
        strType = in.readString();
        isFav = in.readInt() == 1;
        date = in.readString();
        day = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    public MealDB toMealDB() {
        MealDB mealDB = new MealDB();

        mealDB.idMeal = this.idMeal;
        mealDB.strMeal = this.strMeal;
        mealDB.strDrinkAlternate = this.strDrinkAlternate;
        mealDB.strCategory = this.strCategory;
        mealDB.strArea = this.strArea;
        mealDB.strInstructions = this.strInstructions;
        mealDB.strMealThumb = this.strMealThumb;
        mealDB.strTags = this.strTags;
        mealDB.strYoutube = this.strYoutube;
        mealDB.strIngredient1 = this.strIngredient1;
        mealDB.strIngredient2 = this.strIngredient2;
        mealDB.strIngredient3 = this.strIngredient3;
        mealDB.strIngredient4 = this.strIngredient4;
        mealDB.strIngredient5 = this.strIngredient5;
        mealDB.strIngredient6 = this.strIngredient6;
        mealDB.strIngredient7 = this.strIngredient7;
        mealDB.strIngredient8 = this.strIngredient8;
        mealDB.strIngredient9 = this.strIngredient9;
        mealDB.strIngredient10 = this.strIngredient10;
        mealDB.strIngredient11 = this.strIngredient11;
        mealDB.strIngredient12 = this.strIngredient12;
        mealDB.strIngredient13 = this.strIngredient13;
        mealDB.strIngredient14 = this.strIngredient14;
        mealDB.strIngredient15 = this.strIngredient15;
        mealDB.strIngredient16 = this.strIngredient16;
        mealDB.strIngredient17 = this.strIngredient17;
        mealDB.strIngredient18 = this.strIngredient18;
        mealDB.strIngredient19 = this.strIngredient19;
        mealDB.strIngredient20 = this.strIngredient20;
        mealDB.strMeasure1 = this.strMeasure1;
        mealDB.strMeasure2 = this.strMeasure2;
        mealDB.strMeasure3 = this.strMeasure3;
        mealDB.strMeasure4 = this.strMeasure4;
        mealDB.strMeasure5 = this.strMeasure5;
        mealDB.strMeasure6 = this.strMeasure6;
        mealDB.strMeasure7 = this.strMeasure7;
        mealDB.strMeasure8 = this.strMeasure8;
        mealDB.strMeasure9 = this.strMeasure9;
        mealDB.strMeasure10 = this.strMeasure10;
        mealDB.strMeasure11 = this.strMeasure11;
        mealDB.strMeasure12 = this.strMeasure12;
        mealDB.strMeasure13 = this.strMeasure13;
        mealDB.strMeasure14 = this.strMeasure14;
        mealDB.strMeasure15 = this.strMeasure15;
        mealDB.strMeasure16 = this.strMeasure16;
        mealDB.strMeasure17 = this.strMeasure17;
        mealDB.strMeasure18 = this.strMeasure18;
        mealDB.strMeasure19 = this.strMeasure19;
        mealDB.strMeasure20 = this.strMeasure20;
        mealDB.strSource = this.strSource;
        mealDB.strImageSource = this.strImageSource;
        mealDB.strCreativeCommonsConfirmed = this.strCreativeCommonsConfirmed;
        mealDB.dateModified = this.dateModified;
        mealDB.dateAndTime = this.dateAndTime;
        mealDB.isFav = this.isFav;
        mealDB.date = this.date;
        mealDB.day = this.day;

        return mealDB;
    }

    public MealDBWeek toMealDBWeek() {
        MealDBWeek mealDB = new MealDBWeek();

        mealDB.idMeal = this.idMeal;
        mealDB.strMeal = this.strMeal;
        mealDB.strDrinkAlternate = this.strDrinkAlternate;
        mealDB.strCategory = this.strCategory;
        mealDB.strArea = this.strArea;
        mealDB.strInstructions = this.strInstructions;
        mealDB.strMealThumb = this.strMealThumb;
        mealDB.strTags = this.strTags;
        mealDB.strYoutube = this.strYoutube;
        mealDB.strIngredient1 = this.strIngredient1;
        mealDB.strIngredient2 = this.strIngredient2;
        mealDB.strIngredient3 = this.strIngredient3;
        mealDB.strIngredient4 = this.strIngredient4;
        mealDB.strIngredient5 = this.strIngredient5;
        mealDB.strIngredient6 = this.strIngredient6;
        mealDB.strIngredient7 = this.strIngredient7;
        mealDB.strIngredient8 = this.strIngredient8;
        mealDB.strIngredient9 = this.strIngredient9;
        mealDB.strIngredient10 = this.strIngredient10;
        mealDB.strIngredient11 = this.strIngredient11;
        mealDB.strIngredient12 = this.strIngredient12;
        mealDB.strIngredient13 = this.strIngredient13;
        mealDB.strIngredient14 = this.strIngredient14;
        mealDB.strIngredient15 = this.strIngredient15;
        mealDB.strIngredient16 = this.strIngredient16;
        mealDB.strIngredient17 = this.strIngredient17;
        mealDB.strIngredient18 = this.strIngredient18;
        mealDB.strIngredient19 = this.strIngredient19;
        mealDB.strIngredient20 = this.strIngredient20;
        mealDB.strMeasure1 = this.strMeasure1;
        mealDB.strMeasure2 = this.strMeasure2;
        mealDB.strMeasure3 = this.strMeasure3;
        mealDB.strMeasure4 = this.strMeasure4;
        mealDB.strMeasure5 = this.strMeasure5;
        mealDB.strMeasure6 = this.strMeasure6;
        mealDB.strMeasure7 = this.strMeasure7;
        mealDB.strMeasure8 = this.strMeasure8;
        mealDB.strMeasure9 = this.strMeasure9;
        mealDB.strMeasure10 = this.strMeasure10;
        mealDB.strMeasure11 = this.strMeasure11;
        mealDB.strMeasure12 = this.strMeasure12;
        mealDB.strMeasure13 = this.strMeasure13;
        mealDB.strMeasure14 = this.strMeasure14;
        mealDB.strMeasure15 = this.strMeasure15;
        mealDB.strMeasure16 = this.strMeasure16;
        mealDB.strMeasure17 = this.strMeasure17;
        mealDB.strMeasure18 = this.strMeasure18;
        mealDB.strMeasure19 = this.strMeasure19;
        mealDB.strMeasure20 = this.strMeasure20;
        mealDB.strSource = this.strSource;
        mealDB.strImageSource = this.strImageSource;
        mealDB.strCreativeCommonsConfirmed = this.strCreativeCommonsConfirmed;
        mealDB.dateModified = this.dateModified;
        mealDB.dateAndTime = this.dateAndTime;
        mealDB.isFav = this.isFav;
        mealDB.date = this.date;
        mealDB.day = this.day;

        return mealDB;
    }

}
