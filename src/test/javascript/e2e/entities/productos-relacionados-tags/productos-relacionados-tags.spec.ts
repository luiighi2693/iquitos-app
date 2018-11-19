/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    ProductosRelacionadosTagsComponentsPage,
    ProductosRelacionadosTagsDeleteDialog,
    ProductosRelacionadosTagsUpdatePage
} from './productos-relacionados-tags.page-object';

const expect = chai.expect;

describe('ProductosRelacionadosTags e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let productosRelacionadosTagsUpdatePage: ProductosRelacionadosTagsUpdatePage;
    let productosRelacionadosTagsComponentsPage: ProductosRelacionadosTagsComponentsPage;
    let productosRelacionadosTagsDeleteDialog: ProductosRelacionadosTagsDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load ProductosRelacionadosTags', async () => {
        await navBarPage.goToEntity('productos-relacionados-tags');
        productosRelacionadosTagsComponentsPage = new ProductosRelacionadosTagsComponentsPage();
        expect(await productosRelacionadosTagsComponentsPage.getTitle()).to.eq('Productos Relacionados Tags');
    });

    it('should load create ProductosRelacionadosTags page', async () => {
        await productosRelacionadosTagsComponentsPage.clickOnCreateButton();
        productosRelacionadosTagsUpdatePage = new ProductosRelacionadosTagsUpdatePage();
        expect(await productosRelacionadosTagsUpdatePage.getPageTitle()).to.eq('Create or edit a Productos Relacionados Tags');
        await productosRelacionadosTagsUpdatePage.cancel();
    });

    it('should create and save ProductosRelacionadosTags', async () => {
        const nbButtonsBeforeCreate = await productosRelacionadosTagsComponentsPage.countDeleteButtons();

        await productosRelacionadosTagsComponentsPage.clickOnCreateButton();
        await promise.all([productosRelacionadosTagsUpdatePage.setNombreInput('nombre')]);
        expect(await productosRelacionadosTagsUpdatePage.getNombreInput()).to.eq('nombre');
        await productosRelacionadosTagsUpdatePage.save();
        expect(await productosRelacionadosTagsUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await productosRelacionadosTagsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last ProductosRelacionadosTags', async () => {
        const nbButtonsBeforeDelete = await productosRelacionadosTagsComponentsPage.countDeleteButtons();
        await productosRelacionadosTagsComponentsPage.clickOnLastDeleteButton();

        productosRelacionadosTagsDeleteDialog = new ProductosRelacionadosTagsDeleteDialog();
        expect(await productosRelacionadosTagsDeleteDialog.getDialogTitle()).to.eq(
            'Are you sure you want to delete this Productos Relacionados Tags?'
        );
        await productosRelacionadosTagsDeleteDialog.clickOnConfirmButton();

        expect(await productosRelacionadosTagsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
